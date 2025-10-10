package com.example.movieticket.model;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;
    @Setter
    private Double price;
    @Setter
    private Double rating;
    @Setter
    private String imageUrl;

    public Movie() {}

    public Movie(String title, Double price, Double rating, String imageUrl) {
        this.title = title;
        this.price = price;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public Double getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
