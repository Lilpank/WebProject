package com.example.demo.controllers;

import com.example.demo.models.Film;
import com.example.demo.models.Genres;
import com.example.demo.repos.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private FilmRepository filmRepository;


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
}
