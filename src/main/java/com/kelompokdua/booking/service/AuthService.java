package com.kelompokdua.booking.service;

import com.kelompokdua.booking.model.request.AuthRequest;
import com.kelompokdua.booking.model.request.UserRequest;
import com.kelompokdua.booking.model.response.UserResponse;

public interface AuthService {
    
    UserResponse register(UserRequest userRequest);
    String login(AuthRequest authRequest);
}
