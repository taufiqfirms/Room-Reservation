package com.kelompokdua.booking.entity;

import com.kelompokdua.booking.constant.EBookingRoom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_room_booking")
public class RoomBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Rooms room;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "equipmentRequests")
    private List<Equipments> equipmentRequests;

    private Date bookingDate;

    private Date startTime;

    private Date endTime;

    private String description;

    @Enumerated(EnumType.STRING)
    private EBookingRoom status;

    private Long totalPrice;
}
