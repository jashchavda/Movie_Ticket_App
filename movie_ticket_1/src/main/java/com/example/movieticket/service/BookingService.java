package com.example.movieticket.service;

import com.example.movieticket.dto.request.BookingRequest;
import com.example.movieticket.dto.request.UpdateBookingRequest;
import com.example.movieticket.dto.response.BookingResponse;
import com.example.movieticket.exception.BadRequestException;
import com.example.movieticket.exception.ResourceNotFoundException;
import com.example.movieticket.model.Booking;
import com.example.movieticket.model.BookingStatus;
import com.example.movieticket.model.Movie;
import com.example.movieticket.model.User;
import com.example.movieticket.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MovieService movieService;
    private final AuthService authService;

    @Transactional
    public BookingResponse createBooking(Long userId, BookingRequest request) {
        User user = authService.findById(userId);
        Movie movie = movieService.findEntityById(request.getMovieId());

        double totalPrice = movie.getPrice() * request.getSeats();

        Booking booking = Booking.builder()
                .user(user)
                .movie(movie)
                .seats(request.getSeats())
                .showTime(request.getShowTime())
                .totalPrice(totalPrice)
                .status(BookingStatus.CONFIRMED)
                .build();

        return new BookingResponse(bookingRepository.save(booking));
    }

    public List<BookingResponse> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(BookingResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingResponse updateBooking(Long bookingId, Long userId, UpdateBookingRequest request) {
        Booking booking = bookingRepository.findByIdAndUserId(bookingId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + bookingId));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Cannot update a cancelled booking.");
        }

        booking.setSeats(request.getSeats());
        booking.setShowTime(request.getShowTime());
        booking.setTotalPrice(booking.getMovie().getPrice() * request.getSeats());
        booking.setStatus(BookingStatus.UPDATED);

        return new BookingResponse(bookingRepository.save(booking));
    }

    @Transactional
    public void cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findByIdAndUserId(bookingId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + bookingId));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BadRequestException("Booking is already cancelled.");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}