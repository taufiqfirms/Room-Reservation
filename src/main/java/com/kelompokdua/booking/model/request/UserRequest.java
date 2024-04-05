package com.kelompokdua.booking.model.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Division is required")
    private String division;

    @NotBlank(message = "Position is required")
    private String position;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;


}