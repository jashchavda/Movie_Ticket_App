package com.example.movieticket.service;

import com.example.movieticket.dto.response.MovieResponse;
import com.example.movieticket.exception.ResourceNotFoundException;
import com.example.movieticket.model.Movie;
import com.example.movieticket.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(MovieResponse::new)
                .collect(Collectors.toList());
    }

    public MovieResponse getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
        return new MovieResponse(movie);
    }

    public List<MovieResponse> searchByTitle(String keyword) {
        return movieRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(MovieResponse::new)
                .collect(Collectors.toList());
    }

    public List<MovieResponse> getMoviesByLanguage(String language) {
        return movieRepository.findByLanguageIgnoreCase(language)
                .stream()
                .map(MovieResponse::new)
                .collect(Collectors.toList());
    }

    // Internal use only (called by BookingService)
    public Movie findEntityById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    }
}