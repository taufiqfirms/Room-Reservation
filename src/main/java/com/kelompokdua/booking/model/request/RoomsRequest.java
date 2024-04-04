package com.kelompokdua.booking.model.request;

import com.kelompokdua.booking.constan.ERooms;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomsRequest {
    private String name;
    private String roomType;
    private Integer capacity;
    private Long price;
}
