package com.kelompokdua.booking.service.Impl;

import com.kelompokdua.booking.constant.EBookingRoom;
import com.kelompokdua.booking.constant.ERooms;
import com.kelompokdua.booking.entity.*;
import com.kelompokdua.booking.model.request.RoomBookingRequest;
import com.kelompokdua.booking.model.request.UpdateBookingStatusRequest;
import com.kelompokdua.booking.model.response.PaymentResponse;
import com.kelompokdua.booking.model.response.RoomBookingResponse;
import com.kelompokdua.booking.repository.RoomBookingRepository;
import com.kelompokdua.booking.service.*;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final EmailSenderService emailSenderService;


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
        equipmentsService.updateEquipmentById(findByEquipmentId);

        long totalPrice = findRoomsById.getPrice() + (findByEquipmentId.getPrice() * roomBookingRequest.getQtyEquipment());

        RoomBooking trxRoomBooking = RoomBooking.builder()
                .room(findRoomsById)
                .user(findByUserId)
                .equipment(findByEquipmentId)
                .qtyEquipment(roomBookingRequest.getQtyEquipment())
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
    public Page<RoomBooking> getAllBookingRooms(Integer page,
                                                Integer size,
                                                String userId,
                                                String roomId,
                                                String equipmentId,
                                                Integer qtyEquipment,
                                                Date bookingDate,
                                                Date startTime,
                                                Date endTime,
                                                String notes,
                                                EBookingRoom status,
                                                Long totalPrice) {

        if (page == null || page <= 0) {
            page = 1;
        }

        if (size == null || size <= 0) {
            size = 10; // Jumlah default item per halaman
        }

        // Buat objek Pageable untuk paging
        Pageable pageable = PageRequest.of(page - 1, size);

        Specification<RoomBooking> spec = (root, query, criteriaBuilder) -> {
            // Inisialisasi list of predicates
            List<Predicate> predicates = new ArrayList<>();

            // Tambahkan predikat jika nilai parameter tidak null atau kosong
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
            }
            if (roomId != null) {
                predicates.add(criteriaBuilder.equal(root.get("room").get("id"), roomId));
            }
            if (equipmentId != null) {
                predicates.add(criteriaBuilder.equal(root.join("equipment").get("id"), equipmentId));
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
                predicates.add(criteriaBuilder.equal(root.get("description"), notes));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }
            if (totalPrice != null) {
                predicates.add(criteriaBuilder.equal(root.get("totalPrice"), totalPrice));
            }

            // Gabungkan semua predikat dengan operator AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // Dapatkan daftar roomBooking berdasarkan spesifikasi dan paging
        return roomBookingRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public RoomBookingResponse updateBookingStatus(String bookingId, UpdateBookingStatusRequest updateBookingStatusRequest) {
        // Temukan booking berdasarkan ID
        RoomBooking roomBooking = roomBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        // Ubah status booking menjadi ACCEPTED atau REJECTED
        roomBooking.setStatus(updateBookingStatusRequest.getStatus());
        if (updateBookingStatusRequest.getNotes() != null) {
            roomBooking.setNotes(updateBookingStatusRequest.getNotes());
        }
        // Jika status booking menjadi ACCEPTED
        if (updateBookingStatusRequest.getStatus() == EBookingRoom.ACCEPTED) {

            roomBooking.setStatus(EBookingRoom.ACCEPTED);

        } else if (updateBookingStatusRequest.getStatus() == EBookingRoom.REJECTED) {

            roomBooking.setStatus(EBookingRoom.REJECTED);

            Rooms room = roomBooking.getRoom();
            if (room != null) {
                room.setStatus(ERooms.AVAILABLE);
                roomsService.updateRoomById(room);
            }

            Equipments equipment = roomBooking.getEquipment();
            if (equipment != null) {
                int newQuantity = equipment.getQuantity() + roomBooking.getQtyEquipment();
                equipment.setQuantity(newQuantity);
                equipmentsService.updateEquipmentById(equipment);
            }
        }

        // Simpan perubahan ke database
        roomBookingRepository.save(roomBooking);
        emailSenderService.sendEmail(roomBooking);
        // Buat respons untuk memberitahu bahwa operasi telah berhasil
        return RoomBookingResponse.builder()
                .id(roomBooking.getId())
                .user(roomBooking.getUser())
                .room(roomBooking.getRoom())
                .equipmentRequests(roomBooking.getEquipment())
                .bookingDate(roomBooking.getBookingDate())
                .startTime(roomBooking.getStartTime())
                .endTime(roomBooking.getEndTime())
                .totalPrice(roomBooking.getTotalPrice())
                .status(roomBooking.getStatus())
                .build();
    }


}
