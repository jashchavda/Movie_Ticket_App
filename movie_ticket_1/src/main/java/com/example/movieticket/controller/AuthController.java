package com.example.movieticket.controller;

import com.example.movieticket.dto.request.LoginRequest;
import com.example.movieticket.dto.request.RegisterRequest;
import com.example.movieticket.dto.response.UserResponse;
import com.example.movieticket.service.AuthService;
import com.example.movieticket.util.ApiResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse user = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registered successfully", user));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session) {

        UserResponse user = authService.login(request);
        session.setAttribute("userId", user.getId());
        return ResponseEntity.ok(ApiResponse.success("Login successful", user));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.failure("Not authenticated"));
        }
        UserResponse user = new UserResponse(authService.findById(userId));
        return ResponseEntity.ok(ApiResponse.success("Current user", user));
    }
}