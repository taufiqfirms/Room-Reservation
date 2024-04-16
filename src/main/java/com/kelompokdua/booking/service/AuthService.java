package com.kelompokdua.booking.service;

import com.kelompokdua.booking.model.request.*;
import com.kelompokdua.booking.model.response.UserResponse;

public interface AuthService {
    
    UserResponse register(UserRequest userRequest);
    UserResponse registerAdvance(UserAdvanceRequest userAdviceRequest);
    String login(AuthRequest authRequest);
//    AdminResponse registerAdmin(AdminRequest adminRequest);
//    GAResponse registerGA(GARequest gaRequest);
}
