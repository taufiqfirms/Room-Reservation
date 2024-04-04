package com.kelompokdua.booking.model.response;

import com.kelompokdua.booking.constant.ERooms;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponse {
    private String id;
    private String name;
    private String roomType;
    private Integer capacity;
    private List<String> facilities;
    private ERooms status;
    private Long price;
}
