package com.kelompokdua.booking.model.response;

import com.kelompokdua.booking.constan.ERooms;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomsResponse {
    private String id;
    private String name;
    private String roomType;
    private Integer capacity;
    private List<String> facilities;
    private ERooms status;
    private Long price;
}
