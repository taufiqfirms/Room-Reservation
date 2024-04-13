package com.kelompokdua.booking.service;

import com.kelompokdua.booking.model.request.AdminRequest;
import com.kelompokdua.booking.model.request.AuthRequest;
import com.kelompokdua.booking.model.request.GARequest;
import com.kelompokdua.booking.model.request.UserRequest;
import com.kelompokdua.booking.model.response.AdminResponse;
import com.kelompokdua.booking.model.response.GAResponse;
import com.kelompokdua.booking.model.response.UserResponse;

public interface AuthService {
    
    UserResponse register(UserRequest userRequest);
    String login(AuthRequest authRequest);
    AdminResponse registerAdmin(AdminRequest adminRequest);
    GAResponse registerGA(GARequest gaRequest);
}
