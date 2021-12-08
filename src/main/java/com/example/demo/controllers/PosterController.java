package com.example.demo.controllers;

import com.example.demo.models.Comment;
import com.example.demo.models.Film;
import com.example.demo.models.Rating;
import com.example.demo.models.User;
import com.example.demo.repos.CommentRepository;
import com.example.demo.repos.FilmRepository;
import com.example.demo.repos.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/poster")
public class PosterController {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("{title}")
    public String getPageFilm(Model model, @PathVariable String title) {
        Film film = filmRepository.findByTitle(title);
        if (film.getView() != null) {
            Integer countView = film.getView() + 1;
            film.setView(countView);
        } else {
            film.setView(1);
        }

        filmRepository.save(film);
        model.addAttribute("film", film);
        return "pageFilm";
    }

    @PostMapping("{title}")
    public String addComment(@AuthenticationPrincipal User user, @RequestParam int value, @RequestParam String comment, Model model, @PathVariable String title) {
        Film film = filmRepository.findByTitle(title);
        Rating rating = new Rating(user, value);

        Set<Rating> ratingsSet = new HashSet<>(film.getRating());
        ratingsSet.add(rating);
        film.setRating(ratingsSet);
        ratingRepository.save(rating);

        Comment com = new Comment(user, comment, rating, film);
        Set<Comment> commentSet = new HashSet<>(film.getComments());
        commentSet.add(com);
        film.setComments(commentSet);
        commentRepository.save(com);

        model.addAttribute("film", filmRepository.findByTitleOrderByCommentsDesc(title));
        return "pageFilm";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{title}/{user}")
    public String getEditFilm(@PathVariable String title, Model model, @PathVariable String user) {
        Film oneFilm = filmRepository.findByTitle(title);

        model.addAttribute("film", oneFilm);
        return "pageFilmEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("{title}/update")
    public String updateFilm(Model model, @RequestParam("id") Film film, @RequestParam("file") MultipartFile file, @RequestParam Map<String, String> form) throws URISyntaxException, IOException {
        if (form.get("title") == null) {
            throw new IllegalArgumentException("Пришла пустая строка в название фильма");
        }
        film.setGenres(ControllerUtils.getSetGenres(form));
        if (file != null) {
            ControllerUtils.savePicture(film, file, uploadPath);
        }

        if (!form.get("description").isEmpty()) {
            film.setTitle(form.get("description"));
        }
        film.setTitle(form.get("title"));

        filmRepository.save(film);

        URI uri = new URI("/poster/" + film.getTitle().replace(" ", "%20"));

        model.addAttribute("film", film);
        return "redirect:" + uri.toASCIIString();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("{title}/delete")
    public String deleteFilm(Model model, @PathVariable String title){
        filmRepository.delete(filmRepository.findByTitle(title));

        model.addAttribute("films", filmRepository.findAll());
        return "redirect:main";
    }
}
