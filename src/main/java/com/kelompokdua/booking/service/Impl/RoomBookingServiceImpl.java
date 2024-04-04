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
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
        if (!findByEquipmentId.getId().isEmpty()){
            findByEquipmentId = equipmentsService.getEquipmentById(roomBookingRequest.getEquipmentId());
            if (findByEquipmentId.getQuantity() < roomBookingRequest.getQtyEquipment()) {
                throw new RuntimeException("Facility is not available");
            }
            findByEquipmentId.setQuantity(findByEquipmentId.getQuantity() - roomBookingRequest.getQtyEquipment());
            findByEquipmentId = equipmentsService.getEquipmentById(roomBookingRequest.getEquipmentId());

        }
        // Pengecekan ketersediaan fasilitas


        equipmentsService.updateEquipmentById(findByEquipmentId);

        long totalPrice = findRoomsById.getPrice() + (findByEquipmentId.getPrice() * roomBookingRequest.getQtyEquipment());

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
    public List<RoomBooking> getAllBookingRooms(String userId,
                                                        String roomId,
                                                        String equipmentId,
                                                        Integer qtyEquipment,
                                                        Date bookingDate,
                                                        Date startTime,
                                                        Date endTime,
                                                        String notes,
                                                        EBookingRoom status,
                                                        Long totalPrice) {

        Specification<RoomBooking> spec = (root, query, criteriaBuilder) -> {
            // Inisialisasi list of predicates
            List<Predicate> predicates = new ArrayList<>();

            // Tambahkan predikat jika nilai parameter tidak null atau kosong
            if (userId != null) {
                // Dapatkan Nasabah berdasarkan ID
                User user = userService.getUserById(userId);
                predicates.add(criteriaBuilder.equal(root.get("user"), user));
            }
            if (roomId != null) {
                // Dapatkan Nasabah berdasarkan ID
                Rooms rooms = roomsService.getByRoomId(roomId);
                predicates.add(criteriaBuilder.equal(root.get("rooms"), rooms));
            }
            if (equipmentId != null) {
                // Dapatkan Nasabah berdasarkan ID
                Equipments equipments = equipmentsService.getEquipmentById(equipmentId);
                predicates.add(criteriaBuilder.equal(root.get("equipments"), equipments));
            }
            if (qtyEquipment != null) {
                predicates.add(criteriaBuilder.equal(root.get("qtyEquipment"), qtyEquipment));
            }
            if (bookingDate != null) {
                predicates.add(criteriaBuilder.equal(root.get("bookingDate"), bookingDate));
            }
            if (startTime != null) {
                predicates.add(criteriaBuilder.equal(root.get("startTime"), startTime));
            }
            if (endTime != null) {
                predicates.add(criteriaBuilder.equal(root.get("endTime"), endTime));
            }
            if (notes != null) {
                predicates.add(criteriaBuilder.equal(root.get("notes"), notes));
            }
            if (totalPrice != null) {
                predicates.add(criteriaBuilder.equal(root.get("totalPrice"), totalPrice));
            }

            // Gabungkan semua predikat dengan operator AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // Dapatkan daftar roomBooking berdasarkan spesifikasi
        return roomBookingRepository.findAll(spec);

    }

}
