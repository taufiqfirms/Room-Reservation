package com.kelompokdua.booking.model.request;

import com.kelompokdua.booking.constant.EBookingRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class UpdateBookingStatusRequest {
    private EBookingRoom status;
    private String notes;
}
