package com.example.demo.repos;

import com.example.demo.models.Film;
import com.example.demo.models.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmRepository extends CrudRepository<Film, Long>, JpaRepository<Film, Long> {
    List<Film> findFilmsByTitle(String title);

    List<Film> findByGenres(Genres genres);

    Film findByTitle(String title);

    Film findByTitleOrderByCommentsDesc(String title);

}