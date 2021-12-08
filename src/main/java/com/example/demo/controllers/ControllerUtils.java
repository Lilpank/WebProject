package com.example.demo.controllers;

import com.example.demo.models.Film;
import com.example.demo.models.Genres;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {
    public static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    public static void savePicture(Film film, MultipartFile file, String uploadPath) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty() && uploadPath != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + '.' + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            film.setFilename(resultFilename);
        }
    }

    public static Set<Genres> getSetGenres(Map<String, String> form) {
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
        return temp;
    }
}