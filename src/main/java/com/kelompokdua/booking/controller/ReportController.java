package com.kelompokdua.booking.controller;

import com.kelompokdua.booking.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/reports")
@AllArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'GA')")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/booking")
    public ResponseEntity<byte[]> generateBookingReport(
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        ByteArrayInputStream bis = reportService.generateBookingReport(startDate, endDate);
        return generateResponseEntity(bis, "booking_report.csv");
    }

    @GetMapping("/rooms")
    public ResponseEntity<byte[]> generateRoomReport() {
        ByteArrayInputStream bis = reportService.generateRoomReport();
        return generateResponseEntity(bis, "room_report.csv");
    }

    @GetMapping("/equipments")
    public ResponseEntity<byte[]> generateEquipmentReport() {
        ByteArrayInputStream bis = reportService.generateEquipmentReport();
        return generateResponseEntity(bis, "equipment_report.csv");
    }

    @GetMapping("/users")
    public ResponseEntity<byte[]> generateUserReport() {
        ByteArrayInputStream bis = reportService.generateUserReport();
        return generateResponseEntity(bis, "user_report.csv");
    }

    @GetMapping
    public ResponseEntity<byte[]> generateResponseEntity(ByteArrayInputStream bis, String filename) {
        try {
            byte[] reportBytes = bis.readAllBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/csv"));
            headers.setContentDispositionFormData("attachment", filename);

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(reportBytes);
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
