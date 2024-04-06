package com.kelompokdua.booking.service.Impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kelompokdua.booking.entity.UserCredential;
import com.kelompokdua.booking.repository.UserCredentialRepository;
import com.kelompokdua.booking.service.UserCredentialService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService{

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userCredentialRepository.findByUsername(username)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Load by username failed"));
    }

    @Override
    public UserCredential loadByUserId(String userId) {
        return userCredentialRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Load by user id failed"));

    }
}