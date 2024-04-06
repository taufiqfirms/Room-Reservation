package com.kelompokdua.booking.entity;


import com.kelompokdua.booking.constant.ERoomType;
import com.kelompokdua.booking.constant.ERooms;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private ERoomType roomType;

    private Integer capacity;

    private String facilities;

    @Enumerated(EnumType.STRING)
    private ERooms status;

    private Long price;
}
