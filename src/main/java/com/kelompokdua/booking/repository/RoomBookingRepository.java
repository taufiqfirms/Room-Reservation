package com.kelompokdua.booking.repository;

import com.kelompokdua.booking.entity.RoomBooking;
import com.kelompokdua.booking.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, String>, JpaSpecificationExecutor<RoomBooking> {

}
