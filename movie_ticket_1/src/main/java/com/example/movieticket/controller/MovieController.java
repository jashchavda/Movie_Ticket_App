package com.example.movieticket.controller;

import com.example.movieticket.model.Movie;
import com.example.movieticket.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {

    @Autowired
    private MovieRepository movieRepo;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return movieRepo.save(movie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>  selectMovie(@PathVariable Long id) {

            Movie movie = movieRepo.findById(id).orElse(null);
           if(movie == null) {
               return ResponseEntity.notFound().build();
           }

           return ResponseEntity.ok(movieRepo.findById(id).get().getLanguage());



    }
}
