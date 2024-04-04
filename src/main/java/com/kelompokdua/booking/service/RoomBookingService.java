package com.kelompokdua.booking.service;

import com.kelompokdua.booking.entity.RoomBooking;
import com.kelompokdua.booking.model.request.PaymentTransactionRequest;
import com.kelompokdua.booking.model.request.RoomBookingRequest;
import com.kelompokdua.booking.model.response.RoomBookingResponse;

import java.util.List;

public interface RoomBookingService {
    RoomBookingResponse bookedRoom(RoomBookingRequest roomBookingRequest);

    List<RoomBookingResponse> getAll();

}
