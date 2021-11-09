package com.example.demo.controllers;

import com.example.demo.models.Film;
import com.example.demo.models.Genres;
import com.example.demo.models.User;
import com.example.demo.repos.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired
    private FilmRepository filmRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String main(
            @RequestParam(required = false, defaultValue = "") String filter,
            @RequestParam(required = false, defaultValue = "") Genres genre,
            Model model
    ) {
        Iterable<Film> films = filmRepository.findAll();
        if (genre != null) {
            films = filmRepository.findByGenres(genre);

        }

        if (filter != null && !filter.isEmpty()) {
            films = filmRepository.findFilmsByTitle(filter);

        } else if (filter == null) {
            films = filmRepository.findAll();
        }



        model.addAttribute("films", films);
        model.addAttribute("filter", filter);

        return "main";
    }


    @PostMapping("/")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String title,
            @RequestParam Map<String, String> form,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file,
            Model model
    ) throws IOException {
        Film film = new Film(title, description, user);

        Set<String> genres = Arrays.stream(Genres.values())
                .map(Genres::name)
                .collect(Collectors.toSet());

        Set<Genres> temp = new HashSet<>();
        for (String key : form.keySet()) {
            if (genres.contains(key)) {
                if (key != null) {
                    temp.add(Genres.valueOf(key));
                }
            }
        }
        film.setGenres(temp);


        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + '.' + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            film.setFilename(resultFilename);
        }


        filmRepository.save(film);
        Iterable<Film> films = filmRepository.findAll();
        model.addAttribute("films", films);
        return "main";
    }
}
