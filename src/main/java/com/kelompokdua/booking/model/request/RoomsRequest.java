package com.kelompokdua.booking.model.request;

import com.kelompokdua.booking.constant.ERoomType;
import com.kelompokdua.booking.constant.ERooms;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomsRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Room type cannot be blank")
    private ERoomType roomType;

    @NotNull(message = "Capacity cannot be null")
    @Positive(message = "Capacity must be a positive integer")
    private Integer capacity;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive number")
    private Long price;
}