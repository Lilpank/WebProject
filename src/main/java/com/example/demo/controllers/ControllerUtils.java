package com.example.demo.controllers;

import com.example.demo.models.Comment;
import com.example.demo.models.Film;
import com.example.demo.models.Rating;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {
    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    static Integer getMean_rating(Film film) {
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