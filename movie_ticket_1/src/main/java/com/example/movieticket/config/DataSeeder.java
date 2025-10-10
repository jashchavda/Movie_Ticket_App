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
            // Only seed if no movies exist
            if (movieRepository.count() == 0) {
                movieRepository.save(new Movie("The Great Escape", 120.0, 4.3, "https://placehold.co/300x400?text=Movie+1"));
                movieRepository.save(new Movie("Space Odyssey", 150.0, 4.7, "https://placehold.co/300x400?text=Movie+2"));
                movieRepository.save(new Movie("Mystery Night", 100.0, 4.0, "https://placehold.co/300x400?text=Movie+3"));
                movieRepository.save(new Movie("Comedy Club", 80.0, 3.9, "https://placehold.co/300x400?text=Movie+4"));
                System.out.println("✅ Default movies seeded successfully!");
            } else {
                System.out.println("ℹ️ Movies already exist — skipping seeding.");
            }
        };
    }
}
