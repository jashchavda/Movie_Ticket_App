package com.example.movieticket.model;

import jakarta.persistence.*;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    private User user;

    @Setter
    @ManyToOne
    private Movie movie;

    @Setter
    private Integer seats;

    @Setter
    private Double totalPrice;

    @Setter
    private String showTime;


    @Setter
    @Column(name = "seat_numbers", length = 255)
    private String seatNumbers;

    public String getSeatNumbers() {
        return seatNumbers;
    }

    private LocalDateTime createdAt = LocalDateTime.now();

    public Booking() {}

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Movie getMovie() { return movie; }
    public Integer getSeats() { return seats; }
    public Double getTotalPrice() { return totalPrice; }
    public String getShowTime() { return showTime; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
