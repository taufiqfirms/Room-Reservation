package com.kelompokdua.booking.model.request;

import com.kelompokdua.booking.constant.ERooms;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomsSearchRequest {

    private String id;
    private String name;
    private String roomType;
    private Integer minCapacity;
    private Integer capacity;
    private Integer maxCapacity;
    private String facilities;
    private ERooms status;
    private Long minPrice;
    private Long maxPrice;
    private Integer page;
    private Integer size;
}
