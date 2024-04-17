package com.kelompokdua.booking.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAdvanceResponse {

    private String id;

    private String name;

    private String division;

    private String position;

    private String email;

    private String username;

    private List<String> roles;
}