package com.example.movieticket.config;

import com.example.movieticket.model.Movie;
import com.example.movieticket.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(MovieRepository movieRepository) {
        return args -> {

            if (movieRepository.count() == 0) {

                movieRepository.save(new Movie(
                        "The Great Escape",
                        120.0,
                        4.3,
                        "http://localhost:8080/images/the-great-escape.jpeg",
                        "Hindi"
                ));

                movieRepository.save(new Movie(
                        "Space Odyssey",
                        150.0,
                        4.7,
                        "http://localhost:8080/images/space-odyssey.jpeg",
                        "English"
                ));

                movieRepository.save(new Movie(
                        "Skyfall",
                        100.0,
                        4.0,
                        "http://localhost:8080/images/skyfall.jpeg",
                        "Sanskrit"
                ));

                movieRepository.save(new Movie(
                        "Martian",
                        130.0,
                        4.6,
                        "http://localhost:8080/images/martian.jpeg",
                        "Marathi"
                ));



                System.out.println("Default movies seeded successfully with local images!");
            } else {
                System.out.println(" Movies already exist — skipping seeding.");
            }
        };
    }
}
