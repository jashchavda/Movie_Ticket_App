package com.example.movieticket.controller;

import com.example.movieticket.dto.request.BookingRequest;
import com.example.movieticket.dto.request.UpdateBookingRequest;
import com.example.movieticket.dto.response.BookingResponse;
import com.example.movieticket.exception.UnauthorizedException;
import com.example.movieticket.service.BookingService;
import com.example.movieticket.util.ApiResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    private Long getAuthenticatedUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            throw new UnauthorizedException("Please log in to continue.");
        }
        return userId;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(
            @Valid @RequestBody BookingRequest request,
            HttpSession session) {

        Long userId = getAuthenticatedUserId(session);
        BookingResponse booking = bookingService.createBooking(userId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Booking confirmed", booking));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getUserBookings(HttpSession session) {
        Long userId = getAuthenticatedUserId(session);
        return ResponseEntity.ok(
                ApiResponse.success("Bookings fetched", bookingService.getUserBookings(userId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingResponse>> updateBooking(
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookingRequest request,
            HttpSession session) {

        Long userId = getAuthenticatedUserId(session);
        BookingResponse booking = bookingService.updateBooking(id, userId, request);
        return ResponseEntity.ok(ApiResponse.success("Booking updated", booking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> cancelBooking(
            @PathVariable Long id,
            HttpSession session) {

        Long userId = getAuthenticatedUserId(session);
        bookingService.cancelBooking(id, userId);
        return ResponseEntity.ok(ApiResponse.success("Booking cancelled"));
    }
}