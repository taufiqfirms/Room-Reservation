package com.kelompokdua.booking.controller;

import com.kelompokdua.booking.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<byte[]> generateDailyReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        // Generate daily report based on provided date range
        ByteArrayInputStream bis = reportService.generateDailyReport(startDate, endDate);

        try {
            // Convert ByteArrayInputStream to byte array
            byte[] reportBytes = bis.readAllBytes();

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/csv"));
            headers.setContentDispositionFormData("attachment", "daily_report.csv");

            // Return the response entity with byte array and headers
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(reportBytes);
        } finally {
            // Close the ByteArrayInputStream
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}