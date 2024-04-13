package com.kelompokdua.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kelompokdua.booking.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

}
