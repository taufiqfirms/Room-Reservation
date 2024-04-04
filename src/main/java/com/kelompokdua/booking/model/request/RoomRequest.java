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
public class RoomRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Room type cannot be blank")
    private String roomType;

    @NotNull(message = "Capacity cannot be null")
    @Positive(message = "Capacity must be a positive integer")
    private Integer capacity;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive number")
    private Long price;
}
