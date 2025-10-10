package com.example.movieticket.controller;

import com.example.movieticket.model.*;
import com.example.movieticket.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MovieRepository movieRepo;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Map<String, Object> body, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long movieId = Long.valueOf(String.valueOf(body.get("movieId")));
        Integer seats = Integer.valueOf(String.valueOf(body.get("seats")));

        User user = userRepo.findById(userId).get();
        Movie movie = movieRepo.findById(movieId).get();

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setMovie(movie);
        booking.setSeats(seats);
        booking.setTotalPrice(movie.getPrice() * seats);

        bookingRepo.save(booking);
        return ResponseEntity.ok(booking);
    }

    @GetMapping
    public ResponseEntity<?> getBookings(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userRepo.findById(userId).get();
        return ResponseEntity.ok(bookingRepo.findByUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Booking booking = bookingRepo.findById(id).orElse(null);
        if (booking == null) return ResponseEntity.notFound().build();

        Integer seats = Integer.valueOf(String.valueOf(body.get("seats")));
        booking.setSeats(seats);
        booking.setTotalPrice(booking.getMovie().getPrice() * seats);
        bookingRepo.save(booking);
        return ResponseEntity.ok(booking);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id) {
        bookingRepo.deleteById(id);
        return ResponseEntity.ok("Booking cancelled");
    }
}
