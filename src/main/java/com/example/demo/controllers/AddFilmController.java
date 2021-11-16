package com.example.demo.controllers;

import com.example.demo.models.Film;
import com.example.demo.models.Genres;
import com.example.demo.models.User;
import com.example.demo.repos.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AddFilmController {
    @Autowired
    private FilmRepository filmRepository;

    @Value("${upload.path}")
    private String uploadPath;

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
            model.addAttribute("film", null);
            filmRepository.save(film);
        }
        Iterable<Film> films = filmRepository.findAll();
        model.addAttribute("films", films);
        return "main";
    }
}
