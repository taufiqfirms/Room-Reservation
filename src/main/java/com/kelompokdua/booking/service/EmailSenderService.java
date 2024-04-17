package com.kelompokdua.booking.service;

import com.kelompokdua.booking.entity.RoomBooking;

public interface EmailSenderService {
    void sendEmail(RoomBooking roomBooking);
    void sendEmailCheckout(RoomBooking roomBooking);
}
