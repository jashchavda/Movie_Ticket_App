package com.example.movieticket.controller;

import com.example.movieticket.dto.response.MovieResponse;
import com.example.movieticket.service.MovieService;
import com.example.movieticket.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getAllMovies() {
        return ResponseEntity.ok(ApiResponse.success("Movies fetched", movieService.getAllMovies()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponse>> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Movie found", movieService.getMovieById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<MovieResponse>>> search(
            @RequestParam String keyword) {
        return ResponseEntity.ok(
                ApiResponse.success("Search results", movieService.searchByTitle(keyword)));
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getByLanguage(
            @PathVariable String language) {
        return ResponseEntity.ok(
                ApiResponse.success("Movies by language", movieService.getMoviesByLanguage(language)));
    }
}