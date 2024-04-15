package com.kelompokdua.booking.service;

import com.kelompokdua.booking.entity.User;
import com.kelompokdua.booking.entity.UserCredential;
import com.kelompokdua.booking.model.request.UserAdvanceRequest;
import com.kelompokdua.booking.model.request.UserRequest;

import com.kelompokdua.booking.model.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {

    User createEmployee(UserRequest userRequest, UserCredential userCredential);

    Page<User> getAllUser(Integer page, Integer size);

    User getUserById(String id);
    
    User updateUser(User user);

    void  deleteUserById(String id);

    User findByUsername(String name);
    User createAdminOrGA(UserAdvanceRequest userAdvanceRequest, UserCredential userCredential);
}