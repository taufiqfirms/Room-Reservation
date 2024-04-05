package com.kelompokdua.booking.model.response;

import com.kelompokdua.booking.constant.ERoomType;
import com.kelompokdua.booking.constant.ERooms;
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
    private ERoomType roomType;
    private Integer capacity;
    private String facilities;
    private ERooms status;
    private Long price;
}
