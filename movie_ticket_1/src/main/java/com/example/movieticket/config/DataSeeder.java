package com.example.movieticket.config;

import com.example.movieticket.model.Movie;
import com.example.movieticket.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(MovieRepository movieRepository) {
        return args -> {
            if (movieRepository.count() > 0) {
                log.info("Movies already seeded — skipping.");
                return;
            }

            List<Movie> movies = List.of(
                    Movie.builder()
                            .title("The Great Escape")
                            .price(120.0)
                            .rating(4.3)
                            .imageUrl("http://localhost:8080/images/the-great-escape.jpeg")
                            .language("Hindi")
                            .build(),
                    Movie.builder()
                            .title("Space Odyssey")
                            .price(150.0)
                            .rating(4.7)
                            .imageUrl("http://localhost:8080/images/space-odyssey.jpeg")
                            .language("English")
                            .build(),
                    Movie.builder()
                            .title("Skyfall")
                            .price(100.0)
                            .rating(4.0)
                            .imageUrl("http://localhost:8080/images/skyfall.jpeg")
                            .language("English")
                            .build(),
                    Movie.builder()
                            .title("Martian")
                            .price(130.0)
                            .rating(4.6)
                            .imageUrl("http://localhost:8080/images/martian.jpeg")
                            .language("English")
                            .build()
            );

            movieRepository.saveAll(movies);
            log.info("Seeded {} movies successfully.", movies.size());
        };
    }
}