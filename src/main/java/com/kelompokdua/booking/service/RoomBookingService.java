package com.kelompokdua.booking.service;

import com.kelompokdua.booking.constant.EBookingRoom;
import com.kelompokdua.booking.entity.RoomBooking;
import com.kelompokdua.booking.model.request.PaymentTransactionRequest;
import com.kelompokdua.booking.model.request.RoomBookingRequest;
import com.kelompokdua.booking.model.response.RoomBookingResponse;

import java.util.Date;
import java.util.List;

public interface RoomBookingService {
    RoomBookingResponse bookedRoom(RoomBookingRequest roomBookingRequest);

    List<RoomBooking> getAllBookingRooms(String userId, String roomId, String equipmentId, Integer qtyEquipment, Date bookingDate, Date startTime, Date endTime, String notes, EBookingRoom status, Long totalPrice);

}
