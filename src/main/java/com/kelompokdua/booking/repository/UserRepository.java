package com.kelompokdua.booking.repository;

import com.kelompokdua.booking.entity.User;
import com.kelompokdua.booking.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String > {

    User getUserByUserCredential_Username(String username);
    Optional<User> findByEmail(String email);
}
