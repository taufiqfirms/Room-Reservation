package com.kelompokdua.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kelompokdua.booking.entity.GA;

@Repository
public interface GARepository extends JpaRepository<GA, String>{

}
