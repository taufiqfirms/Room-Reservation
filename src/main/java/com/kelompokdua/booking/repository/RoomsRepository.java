package com.kelompokdua.booking.repository;


import com.kelompokdua.booking.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, String > {
}
