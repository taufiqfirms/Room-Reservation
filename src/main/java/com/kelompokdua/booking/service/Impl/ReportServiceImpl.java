package com.kelompokdua.booking.service.Impl;

import com.kelompokdua.booking.entity.Equipments;
import com.kelompokdua.booking.entity.RoomBooking;
import com.kelompokdua.booking.entity.Rooms;
import com.kelompokdua.booking.entity.User;
import com.kelompokdua.booking.repository.EquipmentsRepository;
import com.kelompokdua.booking.repository.RoomBookingRepository;
import com.kelompokdua.booking.repository.RoomsRepository;
import com.kelompokdua.booking.repository.UserRepository;
import com.kelompokdua.booking.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final RoomBookingRepository roomBookingRepository;
    private final RoomsRepository roomsRepository;
    private final EquipmentsRepository equipmentsRepository;
    private final UserRepository userRepository;
    @Override
    public ByteArrayInputStream generateBookingReport(Date startDate, Date endDate) {
        // Set default startDate and endDate if not provided by the user
        if (startDate == null) {
            startDate = new Date(0); // Set to minimum date
        }
        if (endDate == null) {
            endDate = new Date(Long.MAX_VALUE); // Set to maximum date
        }

        List<RoomBooking> bookings;

        // Jika startDate dan endDate adalah default, ambil semua data
        if (startDate.equals(new Date(0)) && endDate.equals(new Date(Long.MAX_VALUE))) {
            bookings = roomBookingRepository.findAll(); // Ambil semua data
        } else {
            // Jika bukan, ambil data sesuai range tanggal
            bookings = roomBookingRepository.findByBookingDateBetween(startDate, endDate);
        }

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

        return new ByteArrayInputStream(csvData.toString().getBytes());
    }


    @Override
    public ByteArrayInputStream generateRoomReport() {
        // Mendapatkan semua kamar dari repositori
        List<Rooms> roomsList = roomsRepository.findAll();

        // Format data ke dalam format CSV
        StringBuilder csvData = new StringBuilder();
        csvData.append("Room ID,Room Name,Room Type,Capacity,Facilities,Status,Price\n");
        for (Rooms room : roomsList) {
            csvData.append(room.getId()).append(",")
                    .append(room.getName()).append(",")
                    .append(room.getRoomType()).append(",")
                    .append(room.getCapacity()).append(",")
                    .append(String.join(",", room.getFacilities())).append(",")
                    .append(room.getStatus()).append(",")
                    .append(room.getPrice()).append("\n");
        }

        // Konversi ke ByteArrayInputStream
        return new ByteArrayInputStream(csvData.toString().getBytes());
    }

    @Override
    public ByteArrayInputStream generateEquipmentReport() {
        // Mendapatkan semua peralatan dari repositori
        List<Equipments> equipmentsList = equipmentsRepository.findAll();

        // Format data ke dalam format CSV
        StringBuilder csvData = new StringBuilder();
        csvData.append("Equipment ID,Equipment,Quantity,Price\n");
        for (Equipments equipment : equipmentsList) {
            csvData.append(equipment.getId()).append(",")
                    .append(equipment.getEquipment()).append(",")
                    .append(equipment.getQuantity()).append(",")
                    .append(equipment.getPrice()).append("\n");
        }

        // Konversi ke ByteArrayInputStream
        return new ByteArrayInputStream(csvData.toString().getBytes());
    }

    @Override
    public ByteArrayInputStream generateUserReport() {
        // Mendapatkan semua pengguna dari repositori
        List<User> userList = userRepository.findAll();

        // Format data ke dalam format CSV
        StringBuilder csvData = new StringBuilder();
        csvData.append("User ID,Name,Division,Position,Email\n");
        for (User user : userList) {
            csvData.append(user.getId()).append(",")
                    .append(user.getName()).append(",")
                    .append(user.getDivision()).append(",")
                    .append(user.getPosition()).append(",")
                    .append(user.getEmail()).append("\n");
        }

        // Konversi ke ByteArrayInputStream
        return new ByteArrayInputStream(csvData.toString().getBytes());
    }

}
