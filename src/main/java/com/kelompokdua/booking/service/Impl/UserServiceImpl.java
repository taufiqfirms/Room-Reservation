package com.kelompokdua.booking.service.Impl;

import com.kelompokdua.booking.constant.ERole;
import com.kelompokdua.booking.entity.User;
import com.kelompokdua.booking.model.request.UserRequest;

import com.kelompokdua.booking.repository.UserRepository;
import com.kelompokdua.booking.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User register(UserRequest userRequest) {

        // Buat objek User baru
        User newUser = User.builder()
                .name(userRequest.getName())
                .division(userRequest.getDivision())
                .position(userRequest.getPosition())
                .email(userRequest.getEmail())
                .roles(ERole.EMPLOYEE)
                .build();

        // Simpan objek User baru ke repository
        User savedUser = userRepository.save(newUser);

        // Buat objek UserResponse dari User yang disimpan

        return User.builder()
                .name(savedUser.getName())
                .division(savedUser.getDivision())
                .position(savedUser.getPosition())
                .email(savedUser.getEmail())
                .build();
    }

    @Override
    public Page<User> getAllUser(Integer page, Integer size) {
        if (page <=0) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page-1, size);
        return userRepository.findAll(pageable);

    }

    @Override
    public User getUserById(String id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) return optionalUser.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id : " + id + " Not Found");

    }

    @Override
    public User updateUser(User user) {
        this.getUserById(user.getId());
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(String id) {
        this.getUserById(id);
        userRepository.deleteById(id);
    }
}