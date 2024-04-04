package com.kelompokdua.booking.model.response;

import com.kelompokdua.booking.constant.EBookingRoom;
import com.kelompokdua.booking.entity.Equipments;
import com.kelompokdua.booking.entity.Payment;
import com.kelompokdua.booking.entity.Rooms;
import com.kelompokdua.booking.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomBookingResponse {

    private String id;

    private User user;

    private Rooms room;

    private Equipments equipmentRequests;

    private Date bookingDate;

    private Date startTime;

    private Date endTime;

    private String notes;

    private EBookingRoom status;

    private Long totalPrice;

    private PaymentResponse paymentResponse;

}

