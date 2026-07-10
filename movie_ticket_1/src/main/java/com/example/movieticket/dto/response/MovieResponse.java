package com.example.movieticket.dto.response;

import com.example.movieticket.model.Movie;
import lombok.Getter;

@Getter
public class MovieResponse {

    private final Long id;
    private final String title;
    private final Double price;
    private final Double rating;
    private final String imageUrl;
    private final String language;

    public MovieResponse(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.price = movie.getPrice();
        this.rating = movie.getRating();
        this.imageUrl = movie.getImageUrl();
        this.language = movie.getLanguage();
    }
}