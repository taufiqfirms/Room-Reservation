package com.kelompokdua.booking.service.Impl;

import com.kelompokdua.booking.constant.EBookingRoom;
import com.kelompokdua.booking.constant.ERooms;
import com.kelompokdua.booking.entity.*;
import com.kelompokdua.booking.model.request.PaymentTransactionRequest;
import com.kelompokdua.booking.model.request.RoomBookingRequest;
import com.kelompokdua.booking.model.request.RoomsRequest;
import com.kelompokdua.booking.model.response.PaymentResponse;
import com.kelompokdua.booking.model.response.RoomBookingResponse;
import com.kelompokdua.booking.repository.Equipment;
import com.kelompokdua.booking.repository.RoomBookingRepository;
import com.kelompokdua.booking.service.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomBookingServiceImpl implements RoomBookingService {

    private final RoomBookingRepository roomBookingRepository;
    private final PaymentService paymentService;
    private final RoomsService roomsService;
    private final UserService userService;
    private final EquipmentsService equipmentsService;


    @Override
    @Transactional
    public RoomBookingResponse bookedRoom(RoomBookingRequest roomBookingRequest) {

        Rooms findRoomsById = roomsService.getByRoomId(roomBookingRequest.getRoomId());

        // Memastikan objek Rooms yang ditemukan tidak null sebelum digunakan
        if (findRoomsById == null) {
            throw new RuntimeException("Room not found with id: " + roomBookingRequest.getRoomId());
        }

        // Memeriksa apakah status ruangan adalah "BOOKED"
        if (findRoomsById.getStatus() == ERooms.BOOKED) {
            throw new RuntimeException("Room is already booked");
        }

        // Ubah status ruangan menjadi "booked"
        findRoomsById.setStatus(ERooms.BOOKED);

        // Menyimpan perubahan status ruangan
        roomsService.updateRoomById(findRoomsById);

        User findByUserId = userService.getUserById(roomBookingRequest.getUserId());

        Equipments findByEquipmentId = equipmentsService.getEquipmentById(roomBookingRequest.getEquipmentId());

        // Pengecekan ketersediaan fasilitas
        if (findByEquipmentId.getQuantity() < 1) {
            throw new RuntimeException("Facility is not available");
        }

        findByEquipmentId.setQuantity(findByEquipmentId.getQuantity() - 1);
        equipmentsService.updateEquipmentById(findByEquipmentId);

        long totalPrice = findRoomsById.getPrice() + findByEquipmentId.getPrice();

        RoomBooking trxRoomBooking = RoomBooking.builder()
                .room(findRoomsById)
                .user(findByUserId)
                .equipment(findByEquipmentId)
                .bookingDate(roomBookingRequest.getBookingDate())
                .startTime(roomBookingRequest.getStartTime())
                .endTime(roomBookingRequest.getEndTime())
                .totalPrice(totalPrice)
                .status(EBookingRoom.PENDING)
                .build();

        RoomBooking roomBookingSaved = roomBookingRepository.saveAndFlush(trxRoomBooking);

        Payment payment = paymentService.create(roomBookingSaved);
        roomBookingRepository.saveAndFlush(roomBookingSaved);

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .id(payment.getId())
                .token(payment.getToken())
                .redirectUrl(payment.getRedirectUrl())
                .transactionStatus(payment.getTransactionStatus())
                .build();

        return RoomBookingResponse.builder()
                .id(roomBookingSaved.getId())
                .user(roomBookingSaved.getUser())
                .room(roomBookingSaved.getRoom())
                .equipmentRequests(roomBookingSaved.getEquipment())
                .bookingDate(roomBookingSaved.getBookingDate())
                .startTime(roomBookingSaved.getStartTime())
                .endTime(roomBookingSaved.getEndTime())
                .status(roomBookingSaved.getStatus())
                .totalPrice(roomBookingSaved.getTotalPrice())
                .paymentResponse(paymentResponse)
                .build();
    }


    @Override
    public List<RoomBookingResponse> getAll() {
        return null;
    }
}
