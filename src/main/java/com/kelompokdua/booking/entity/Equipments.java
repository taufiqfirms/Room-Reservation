package com.kelompokdua.booking.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_equipment_request")
public class Equipments {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)// tadinya .IDENTITY
    private String id;
    @ManyToOne
    @JoinColumn(name = "room_booking_id")
    private RoomBooking roomBooking;
    @Column(unique = true)
    private String equipment;
    private Integer quantity;
    private Long price;
}
