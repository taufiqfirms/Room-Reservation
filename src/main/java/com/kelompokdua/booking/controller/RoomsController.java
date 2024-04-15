package com.kelompokdua.booking.controller;

import com.kelompokdua.booking.constant.ERooms;
import com.kelompokdua.booking.entity.Rooms;
import com.kelompokdua.booking.model.request.RoomsRequest;
import com.kelompokdua.booking.model.request.RoomsSearchRequest;
import com.kelompokdua.booking.model.response.PagingResponse;
import com.kelompokdua.booking.model.response.RoomsResponse;
import com.kelompokdua.booking.model.response.WebResponse;
import com.kelompokdua.booking.service.RoomsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/rooms")
@SecurityRequirement(name = "enigmaAuth")
public class RoomsController {

    private final RoomsService roomsService;
    @PreAuthorize("hasAnyRole('ADMIN', 'GA')")
    @PostMapping
    public ResponseEntity<WebResponse<RoomsResponse>> createdRooms(
            @RequestBody RoomsRequest roomRequest) {
        RoomsResponse roomResponse = roomsService.createRoom(roomRequest);
        WebResponse<RoomsResponse> response = WebResponse.<RoomsResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Room Has Created And Register")
                .data(roomResponse)
                .build();
        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN', 'GA')")
    @GetMapping
    public ResponseEntity<?> getAllRooms(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) Integer maxCapacity,
            @RequestParam(required = false) String facilities,
            @RequestParam(required = false) ERooms status,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice){
        RoomsSearchRequest searchRoomRequest = RoomsSearchRequest.builder()
                .id(id)
                .name(name)
                .roomType(roomType)
                .minCapacity(minCapacity)
                .capacity(capacity)
                .maxCapacity(maxCapacity)
                .facilities(facilities)
                .status(status)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .page(page)
                .size(size)
                .build();
        Page<Rooms> roomList = roomsService.findAllRooms(searchRoomRequest);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(roomList.getTotalPages())
                .totalElement(roomList.getTotalElements())
                .build();
        WebResponse<List<Rooms>> response = WebResponse.<List<Rooms>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get All Room List")
                .paging(pagingResponse)
                .data(roomList.getContent())
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GA')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<WebResponse<Rooms>> getRoomById(@PathVariable String id){
        Rooms findRoomsById = roomsService.getByRoomId(id);
        WebResponse<Rooms> response = WebResponse.<Rooms>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get Room By ID")
                .data(findRoomsById)
                .build();
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'GA')")
    @PutMapping
    public ResponseEntity<WebResponse<Rooms>> UpdateRoomById(@RequestBody Rooms rooms) {
        Rooms updateRoomById = roomsService.updateRoomById(rooms);
        WebResponse<Rooms> response = WebResponse.<Rooms>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Update Room By ID")
                .data(updateRoomById)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GA')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<WebResponse<String>> deleteRoomById(@PathVariable String id) {
        roomsService.deleteRoomById(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Delete Room")
                .data("OK")
                .build();
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN', 'GA')")
    @GetMapping(path = "/available")
    public ResponseEntity<?> getAvailableRoom(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<Rooms> roomsList = roomsService.getAllAvailableRooms(page, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(roomsList.getTotalPages())
                .totalElement(roomsList.getTotalElements())
                .build();
        WebResponse<List<Rooms>> response = WebResponse.<List<Rooms>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success Get All Room List")
                .paging(pagingResponse)
                .data(roomsList.getContent())
                .build();
        return ResponseEntity.ok(response);
    }
}
