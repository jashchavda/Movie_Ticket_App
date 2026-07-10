package com.example.movieticket.dto.response;

import com.example.movieticket.model.Booking;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class BookingResponse {

    private final Long id;
    private final MovieResponse movie;
    private final Integer seats;
    private final Double totalPrice;
    private final String showTime;
    private final String status;
    private final LocalDateTime createdAt;

    public BookingResponse(Booking booking) {
        this.id = booking.getId();
        this.movie = new MovieResponse(booking.getMovie());
        this.seats = booking.getSeats();
        this.totalPrice = booking.getTotalPrice();
        this.showTime = booking.getShowTime();
        this.status = booking.getStatus().name();
        this.createdAt = booking.getCreatedAt();
    }
}