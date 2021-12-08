package com.example.demo.controllers;

import com.example.demo.models.Film;
import com.example.demo.models.User;
import com.example.demo.repos.FilmRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;


@Controller
public class AddFilmController {
    @Value("${upload.path}")
    private String uploadPath;

    private final FilmRepository filmRepository;

    public AddFilmController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addFilm")
    public String getAddFilm() {
        return "addFilm";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addFilm")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Film film,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file,
            @RequestParam Map<String, String> form
    ) throws IOException {
        film.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("film", film);
            return "addFilm";

        } else {
            film.setGenres(ControllerUtils.getSetGenres(form));
            ControllerUtils.savePicture(film, file, uploadPath);
            model.addAttribute("film", null);
            filmRepository.save(film);
        }
        Iterable<Film> films = filmRepository.findAll();
        model.addAttribute("films", films);
        return "main";
    }
}
