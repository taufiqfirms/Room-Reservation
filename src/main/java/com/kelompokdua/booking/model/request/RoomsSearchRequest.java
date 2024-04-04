package com.kelompokdua.booking.model.request;

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
    private Integer capacity;
    private String facilities;
    private String status;
    private Long minPrice;
    private Long maxPrice;
}
