package com.kelompokdua.booking.model.request;

import com.kelompokdua.booking.entity.RoomBooking;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentsSearchRequest {

    private String id;
    private String equipment;
    private Integer minQuantity;
    private Integer maxQuantity;
    private Long minPrice;
    private Long maxPrice;
    private Integer page;
    private Integer size;
}
