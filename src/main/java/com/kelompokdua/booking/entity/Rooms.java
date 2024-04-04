package com.kelompokdua.booking.entity;

import com.kelompokdua.booking.constan.ERooms;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_rooms")
public class Rooms {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true)
    private String name;
    private String roomType;
    private Integer capacity;
    private String facilities;
    @Enumerated(EnumType.STRING)
    private ERooms status;
    private Long price;
}
