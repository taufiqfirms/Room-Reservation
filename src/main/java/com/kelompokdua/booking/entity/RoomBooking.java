package com.kelompokdua.booking.entity;

import com.kelompokdua.booking.constan.ETrxBookingStatus;
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

    @OneToMany(mappedBy = "roomBooking", cascade = CascadeType.ALL)
    private List<Equipments> equipmentRequests = new ArrayList<>();

    private Date bookingDate;

    private Date startTime;

    private Date endTime;

    private String description;

    @Enumerated(EnumType.STRING)
    private ETrxBookingStatus status;

    private Date totalPrice;
}
