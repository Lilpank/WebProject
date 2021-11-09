package com.example.demo.controllers;

import com.example.demo.repos.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddFilmController {
    @Autowired
    FilmRepository filmRepository;

    @GetMapping("/addFilm")
    public String getAddFilm() {
        return "addFilm";
    }
}
