package com.kelompokdua.booking.service.Impl;

import com.kelompokdua.booking.entity.RoomBooking;
import com.kelompokdua.booking.repository.RoomBookingRepository;
import com.kelompokdua.booking.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final RoomBookingRepository roomBookingRepository;

    @Override
    public ByteArrayInputStream generateDailyReport(Date startDate, Date endDate) {
        // Set default startDate and endDate if not provided by the user
        if (startDate == null) {
            startDate = new Date(0); // Set to minimum date
        }
        if (endDate == null) {
            endDate = new Date(Long.MAX_VALUE); // Set to maximum date
        }

        List<RoomBooking> bookings = roomBookingRepository.findByBookingDateBetween(startDate, endDate);

        // Format data ke dalam format CSV
        StringBuilder csvData = new StringBuilder();
        csvData.append("Booking ID,Employee Name,Room Name,Booking Date,Start Time,End Time,Description,Status\n");
        for (RoomBooking booking : bookings) {
            csvData.append(booking.getId()).append(",")
                    .append(booking.getUser().getName()).append(",")
                    .append(booking.getRoom().getName()).append(",")
                    .append(booking.getBookingDate()).append(",")
                    .append(booking.getStartTime()).append(",")
                    .append(booking.getEndTime()).append(",")
                    .append(booking.getNotes()).append(",")
                    .append(booking.getStatus()).append("\n");
        }

        // Konversi ke ByteArrayInputStream
        return new ByteArrayInputStream(csvData.toString().getBytes());
    }
}
