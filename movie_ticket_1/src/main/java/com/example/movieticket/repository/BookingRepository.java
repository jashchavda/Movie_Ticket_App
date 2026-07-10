package com.example.movieticket.repository;

import com.example.movieticket.model.Booking;
import com.example.movieticket.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByUserIdAndStatus(Long userId, BookingStatus status);
    Optional<Booking> findByIdAndUserId(Long bookingId, Long userId);
}