package com.kelompokdua.booking.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentResponse {
    private String equipment;
    private Integer quantity;
    private Long price;
}
