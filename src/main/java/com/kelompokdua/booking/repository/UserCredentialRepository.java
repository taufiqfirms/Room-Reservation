package com.kelompokdua.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelompokdua.booking.entity.UserCredential;

public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {
    
    Optional<UserCredential> findByUsername(String username);
}
