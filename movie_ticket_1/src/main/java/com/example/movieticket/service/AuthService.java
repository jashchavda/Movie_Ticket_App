package com.example.movieticket.service;

import com.example.movieticket.dto.request.LoginRequest;
import com.example.movieticket.dto.request.RegisterRequest;
import com.example.movieticket.dto.response.UserResponse;
import com.example.movieticket.exception.BadRequestException;
import com.example.movieticket.exception.UnauthorizedException;
import com.example.movieticket.model.User;
import com.example.movieticket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username '" + request.getUsername() + "' is already taken.");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser);
    }

    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid username or password.");
        }

        return new UserResponse(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Session expired. Please log in again."));
    }
}