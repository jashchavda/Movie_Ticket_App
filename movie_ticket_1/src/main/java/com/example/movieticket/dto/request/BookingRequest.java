package com.example.movieticket.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookingRequest {

    @NotNull(message = "Movie ID is required")
    private Long movieId;

    @NotNull(message = "Number of seats is required")
    @Min(value = 1, message = "At least 1 seat must be booked")
    @Max(value = 10, message = "Cannot book more than 10 seats at once")
    private Integer seats;

    @NotBlank(message = "Show time is required")
    private String showTime;
}