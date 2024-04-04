package com.kelompokdua.booking.repository;

import com.kelompokdua.booking.entity.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, String> {

}
