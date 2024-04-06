package com.kelompokdua.booking.repository;

import com.kelompokdua.booking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String > {

    User getUserByUserCredential_Username(String username);
}
