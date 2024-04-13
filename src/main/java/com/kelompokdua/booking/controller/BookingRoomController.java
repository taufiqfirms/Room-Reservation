package com.kelompokdua.booking.controller;

import com.kelompokdua.booking.constant.EBookingRoom;
import com.kelompokdua.booking.entity.RoomBooking;
import com.kelompokdua.booking.model.request.RoomBookingRequest;
import com.kelompokdua.booking.model.request.UpdateBookingStatusRequest;
import com.kelompokdua.booking.model.response.PagingResponse;
import com.kelompokdua.booking.model.response.RoomBookingResponse;
import com.kelompokdua.booking.model.response.WebResponse;
import com.kelompokdua.booking.service.RoomBookingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/booking")
@SecurityRequirement(name = "enigmaAuth")
public class BookingRoomController {
    private final RoomBookingService roomBookingService;

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @PostMapping("/booked")
    public ResponseEntity<?> bookRoom(@RequestBody RoomBookingRequest roomBookingRequest) {

        RoomBookingResponse response = roomBookingService.bookedRoom(roomBookingRequest);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @PreAuthorize("hasAnyRole('ADMIN', 'GA')")
    @GetMapping("/all")
    public ResponseEntity<WebResponse<Page<RoomBooking>>> getAllBookingRooms(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String roomId,
            @RequestParam(required = false) String equipmentId,
            @RequestParam(required = false) Integer qtyEquipment,
            @RequestParam(required = false) Date bookingDate,
            @RequestParam(required = false) Date startTime,
            @RequestParam(required = false) Date endTime,
            @RequestParam(required = false) String notes,
            @RequestParam(required = false) EBookingRoom status,
            @RequestParam(required = false) Long totalPrice) {

        Page<RoomBooking> roomBookings = roomBookingService.getAllBookingRooms(
                page, size, userId, roomId, equipmentId, qtyEquipment, bookingDate, startTime, endTime, notes, status, totalPrice);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(roomBookings.getTotalPages())
                .totalElement(roomBookings.getTotalElements())
                .build();

        WebResponse<Page<RoomBooking>> response = WebResponse.<Page<RoomBooking>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success get room bookings")
                .paging(pagingResponse)
                .data(roomBookings)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GA', 'EMPLOYEE')")
    @PutMapping("/status/{bookingId}")
    public ResponseEntity<RoomBookingResponse> updateBookingStatus(
            @PathVariable("bookingId") String bookingId,
            @RequestBody UpdateBookingStatusRequest updateBookingStatusRequest) {

        RoomBookingResponse response = roomBookingService.updateBookingStatus(bookingId, updateBookingStatusRequest);

        return ResponseEntity.ok(response);
    }

}
