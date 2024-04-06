package com.kelompokdua.booking.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kelompokdua.booking.entity.User;
import com.kelompokdua.booking.model.response.WebResponse;
import com.kelompokdua.booking.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('EMPLOYEE')")
@Slf4j
@SecurityRequirement(name = "enigmaAuth")
public class UserProfileController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("Username : " + username);
        User user = userService.findByUsername(username);
        WebResponse<User> response = WebResponse.<User>builder()
        .status(HttpStatus.OK.getReasonPhrase())
        .message("Success get employee")
        .data(user)
        .build();
        return ResponseEntity.ok(response);
    }
}
