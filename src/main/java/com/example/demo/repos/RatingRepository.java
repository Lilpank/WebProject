package com.example.demo.repos;

import com.example.demo.models.Rating;
import com.example.demo.models.User;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    Rating findByUser(User user);
}
