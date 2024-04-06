package com.kelompokdua.booking.service;

import com.kelompokdua.booking.entity.User;
import com.kelompokdua.booking.model.request.UserRequest;

import com.kelompokdua.booking.model.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {

    UserResponse register(UserRequest userRequest);

    Page<User> getAllUser(Integer page, Integer size);

    User getUserById(String id);
    User updateUser(User user);

    void  deleteUserById(String id);

}