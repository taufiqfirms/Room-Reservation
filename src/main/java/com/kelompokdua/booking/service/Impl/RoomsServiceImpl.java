package com.kelompokdua.booking.service.Impl;

import com.kelompokdua.booking.constant.ERoomType;
import com.kelompokdua.booking.constant.ERooms;
import com.kelompokdua.booking.entity.Rooms;

import com.kelompokdua.booking.entity.User;
import com.kelompokdua.booking.model.request.RoomsRequest;
import com.kelompokdua.booking.model.request.RoomsSearchRequest;
import com.kelompokdua.booking.model.response.RoomsResponse;
import com.kelompokdua.booking.repository.RoomsRepository;
import com.kelompokdua.booking.service.RoomsService;

import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@AllArgsConstructor
public class RoomsServiceImpl implements RoomsService {

    private final RoomsRepository roomsRepository;

    @Override
    public RoomsResponse createRoom(RoomsRequest roomsRequest) {


        Map<ERoomType, String> facilitiesMap = new HashMap<>();
        facilitiesMap.put(ERoomType.ECONOMIC, "Air Conditioner, Bathroom, Closet, Wifi");
        facilitiesMap.put(ERoomType.STANDARD, "TV, Air Conditioner, Closet, Bathroom, WiFi");
        facilitiesMap.put(ERoomType.BUSINESS, "40 inch TV, Air Conditioner, Closet, Bathroom, Free WiFi, Recliner, Intercom Phone");
        facilitiesMap.put(ERoomType.VIP, "70 inch Tv, Air Conditioner, Closet, Bathroom, Free WiFi, Recliner, Bed,  Jacuzzi, Mini Bar, Balcony, Living Room, Intercom Phone");
        facilitiesMap.put(ERoomType.SUPERIORS, "100 inch Tv, Air Conditioner, Closet, Bathroom, Free WiFi,  Living Room, King Size Bed, Spa, Gym, Vending Machine Area, Dining Area, Balcony, Pool Access, Intercom Phone");


        Rooms newRoom = Rooms.builder()
                .name(roomsRequest.getName())
                .roomType(roomsRequest.getRoomType())
                .capacity(roomsRequest.getCapacity())
                .facilities(facilitiesMap.get(roomsRequest.getRoomType()))
                .status(ERooms.AVAILABLE)
                .price(roomsRequest.getPrice())
                .build();

        Rooms saveRoom = roomsRepository.saveAndFlush(newRoom);
        return RoomsResponse.builder()
                .id(saveRoom.getId())
                .name(saveRoom.getName())
                .roomType(saveRoom.getRoomType())
                .capacity(saveRoom.getCapacity())
                .facilities(saveRoom.getFacilities())
                .price(saveRoom.getPrice())
                .status(ERooms.AVAILABLE)
                .build();
    }

    @Override
    public Page<Rooms> findAllRooms(RoomsSearchRequest request) {
        if (request.getPage() == null || request.getPage() <= 0) {
            request.setPage(1);
        }

        if (request.getSize() == null || request.getSize() <= 0) {
            request.setSize(10); // Jumlah default item per halaman
        }

        // Buat objek Pageable untuk paging
        Pageable pageable = PageRequest.of(
                request.getPage() - 1, request.getSize());

        Specification<Rooms> spec = (root, query, criteriaBuilder) -> {
            // Inisialisasi list of predicates
            List<Predicate> predicates = new ArrayList<>();

            // Tambahkan predikat jika nilai parameter tidak null atau kosong
            if (request.getId() != null && !request.getId().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("id"), request.getId()));
            }
            if (request.getName() != null && !request.getName().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("name"), request.getName()));
            }
            if (request.getRoomType() != null && !request.getRoomType().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("roomType"), request.getRoomType()));
            }
            if (request.getMinCapacity() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("capacity"), request.getMinCapacity()));
            }
            if (request.getCapacity() != null) {
                predicates.add(criteriaBuilder.equal(root.get("capacity"), request.getCapacity()));
            }
            if (request.getMaxCapacity() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("capacity"), request.getMaxCapacity()));
            }
            if (request.getFacilities() != null && !request.getFacilities().isEmpty()) {
                predicates.add(criteriaBuilder.like
                        (root.<String>get("facilities"), "%"+request.getFacilities()+"%"));
            }
            if (request.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), request.getStatus()));
            }
            if (request.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("price"), request.getMinPrice()));
            }
            if (request.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("price"), request.getMaxPrice()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // Dapatkan daftar room berdasarkan spesifikasi dan paging
        return roomsRepository.findAll(spec, pageable);
    }


    @Override
    public Rooms getByRoomId(String id) {
        Optional<Rooms> optionalRooms = roomsRepository.findById(id);
        if (optionalRooms.isPresent()) {
            return optionalRooms.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room ID Not Found");
    }

    @Override
    public Rooms updateRoomById(Rooms rooms) {
        this.getByRoomId(rooms.getId());
        return roomsRepository.save(rooms);
    }

    @Override
    public void deleteRoomById(String id) {
        this.getByRoomId(id);
        roomsRepository.deleteById(id);

    }
    @Override
    public Page<Rooms> getAllAvailableRooms(Integer page, Integer size) {
        if (page == null || page <= 0) {
            page = 1;
        }

        if (size == null || size <= 0) {
            size = 10;
        }
        ERooms setStatus = ERooms.AVAILABLE;
        Specification<Rooms> findRoom = (root, query, criteriaBuilder) -> {
            Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), setStatus);
            return criteriaBuilder.or(statusPredicate);
        };
        Pageable pageable = PageRequest.of(page-1, size);
        return roomsRepository.findAll(findRoom, pageable);
    }

    @Override
    public Rooms findUserByName(String name) {
        Optional<Rooms> roomsOptional = roomsRepository.findByName(name);
        return roomsOptional.orElse(null);
    }
}
