package com.kelompokdua.booking.model.response;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentsResponse {
    private String equipment;
    private Integer quantity;
    private Long price;
}
