package com.kelompokdua.booking.service;

import com.kelompokdua.booking.entity.Payment;
import com.kelompokdua.booking.entity.RoomBooking;

public interface PaymentService {
    Payment create(RoomBooking roomBooking);

}
