package com.kelompokdua.booking.controller;

import com.kelompokdua.booking.entity.RoomBooking;
import com.kelompokdua.booking.model.request.RoomBookingRequest;
import com.kelompokdua.booking.model.response.RoomBookingResponse;
import com.kelompokdua.booking.service.RoomBookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/booking")
public class BookingRoomController {
    private final RoomBookingService roomBookingService;


    @PostMapping("/booked")
    public ResponseEntity<?> bookRoom(@RequestBody RoomBookingRequest roomBookingRequest) {

        RoomBookingResponse response = roomBookingService.bookedRoom(roomBookingRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
