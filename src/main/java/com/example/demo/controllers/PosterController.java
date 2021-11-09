package com.example.demo.controllers;

import com.example.demo.models.Comment;
import com.example.demo.models.Film;
import com.example.demo.models.Rating;
import com.example.demo.models.User;
import com.example.demo.repos.CommentRepository;
import com.example.demo.repos.FilmRepository;
import com.example.demo.repos.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/poster")
public class PosterController {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("{title}")
    public String getPageFilm(Model model, @PathVariable String title) {
        Film film = filmRepository.findByTitleOrderByCommentsDesc(title);
        if (film.getView() != null) {
            Integer countView = film.getView() + 1;
            film.setView(countView);
        } else {
            film.setView(1);
        }

        filmRepository.save(film);
        model.addAttribute("mean_rating", getMean_rating(title));
        model.addAttribute("film", film);
        return "pageFilm";
    }

    @PostMapping("{title}")
    public String addComment(
            @AuthenticationPrincipal User user,
            @RequestParam int value,
            @RequestParam String comment,
            Model model,
            @PathVariable String title
    ) {
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


        model.addAttribute("mean_rating", getMean_rating(title));
        model.addAttribute("film", filmRepository.findByTitleOrderByCommentsDesc(title));
        return "pageFilm";
    }

    private Integer getMean_rating(String title) {
        Film film = filmRepository.findByTitle(title);
        int mean_rating = 0;
        for (Rating ratings : film.getRating()) {
            if (ratings.getValue() != null) {
                mean_rating = mean_rating + ratings.getValue();
            }
        }

        int count = 0;
        if (film.getComments().size() != 0) {
            for (Comment comm : film.getComments()) {
                if (comm != null) {
                    count = count + 1;
                }
            }
            mean_rating = mean_rating / count;
        }
        return mean_rating;
    }
}
