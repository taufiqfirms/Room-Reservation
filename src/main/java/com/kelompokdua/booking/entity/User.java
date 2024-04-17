package com.kelompokdua.booking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String id;

    private String name;

    private String division;

    private String position;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private ERole roles;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_credential_id", referencedColumnName = "id")
    @JsonIgnore
    private UserCredential userCredential;

}