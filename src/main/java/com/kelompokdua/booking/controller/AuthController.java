package com.kelompokdua.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelompokdua.booking.model.request.AuthRequest;
import com.kelompokdua.booking.model.request.UserRequest;
import com.kelompokdua.booking.model.response.UserResponse;
import com.kelompokdua.booking.model.response.WebResponse;
import com.kelompokdua.booking.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        String token = authService.login(authRequest);
        WebResponse<String> response = WebResponse.<String>builder()
        .status(HttpStatus.CREATED.getReasonPhrase())
        .message("Success login")
        .data(token)
        .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequest userRequest){
        UserResponse userResponse = authService.register(userRequest);
        WebResponse<UserResponse> response = WebResponse.<UserResponse>builder()
        .status(HttpStatus.CREATED.getReasonPhrase())
        .message("Success register new employee")
        .data(userResponse)
        .build();
        return ResponseEntity.ok(response);
    }
}
