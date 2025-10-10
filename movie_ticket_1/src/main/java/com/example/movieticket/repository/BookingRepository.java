package com.example.movieticket.repository;

import com.example.movieticket.model.Booking;
import com.example.movieticket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
}
