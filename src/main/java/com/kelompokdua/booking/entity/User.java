package com.kelompokdua.booking.entity;

import com.kelompokdua.booking.constant.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String  id;

    private String name;

    private String division;

    private String position;

    private String email;

}
