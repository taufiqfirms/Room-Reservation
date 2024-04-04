package com.kelompokdua.booking.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.kelompokdua.booking.entity.UserCredential;

public interface UserCredentialService extends UserDetailsService{

    UserCredential loadByUserId(String userId);
}
