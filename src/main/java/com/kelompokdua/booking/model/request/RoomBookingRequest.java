package com.kelompokdua.booking.model.request;

import com.kelompokdua.booking.constant.EBookingRoom;
import com.kelompokdua.booking.entity.Equipments;
import com.kelompokdua.booking.entity.Payment;
import com.kelompokdua.booking.entity.Rooms;
import com.kelompokdua.booking.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
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
public class RoomBookingRequest {

    @NotEmpty
    private String userId;

    @NotEmpty
    private String roomId;

    private List<String> equipmentId;

    @NotNull
    @Future
    private Date bookingDate;

    @NotNull
    @Future
    private Date startTime;

    @NotNull
    @Future
    private Date endTime;

    @Size(max = 255)
    private String description;

    @NotNull
    private EBookingRoom status;

    @NotNull
    private Long totalPrice;

    private Payment payment;

}
