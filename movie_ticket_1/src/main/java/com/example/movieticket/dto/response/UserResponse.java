package com.example.movieticket.dto.response;

import com.example.movieticket.model.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String username;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}