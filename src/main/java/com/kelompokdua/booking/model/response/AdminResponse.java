package com.kelompokdua.booking.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponse {

    private String id;

    private String name;

    private String email;

    private String username;

    private List<String> roles;
}
