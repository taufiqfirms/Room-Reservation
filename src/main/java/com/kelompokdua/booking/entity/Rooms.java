package com.kelompokdua.booking.entity;


import com.kelompokdua.booking.constant.ERooms;
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
@Table(name = "t_rooms")
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String roomType;

    private int capacity;

    private String facilities;

    @Enumerated(EnumType.STRING)
    private ERooms status;

    private Long price;
}