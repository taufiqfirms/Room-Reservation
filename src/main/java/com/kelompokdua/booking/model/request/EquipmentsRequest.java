package com.kelompokdua.booking.model.request;

import com.kelompokdua.booking.entity.RoomBooking;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentsRequest {
    private String id;
    private RoomBooking roomBooking;
    private String equipment;
    private Integer quantity;
    private Long price;
}
