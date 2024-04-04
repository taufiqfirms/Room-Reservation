package com.kelompokdua.booking.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EquipmentRequest {
    @NotBlank(message = "Equipment name cannot be blank")
    private String equipment;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be a positive integer")
    private Integer quantity;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive number")
    private Long price;
}
