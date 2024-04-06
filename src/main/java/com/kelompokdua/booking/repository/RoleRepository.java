package com.kelompokdua.booking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kelompokdua.booking.constant.ERole;
import com.kelompokdua.booking.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
    
    Optional<Role> findByRole(ERole role);
}
