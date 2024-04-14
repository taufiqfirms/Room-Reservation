package com.kelompokdua.booking.service.Impl;
import com.kelompokdua.booking.entity.RoomBooking;
import com.kelompokdua.booking.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(RoomBooking roomBooking) {
        StringBuilder messageBody = new StringBuilder();
        messageBody.append("Dear ").append(roomBooking.getUser().getName()).append(",\n\n");
        messageBody.append("We would like to inform you about the recent update regarding your booking:\n\n");
        messageBody.append("Booking ID: ").append(roomBooking.getId()).append("\n");
        messageBody.append("Room: ").append(roomBooking.getRoom().getName()).append("\n");
        messageBody.append("Check-in Date: ").append(roomBooking.getStartTime()).append("\n");
        messageBody.append("Check-out Date: ").append(roomBooking.getEndTime()).append("\n");
        messageBody.append("Status: ").append(roomBooking.getStatus()).append("\n\n");
        messageBody.append("Thank you for choosing our service. If you have any further questions or concerns, please don't hesitate to contact us.\n\n");
        messageBody.append("Best regards,\n");
        messageBody.append("Kelompok 2 Enigma Camp 2024");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kelompokduaenigma@gmail.com");
        message.setTo(roomBooking.getUser().getEmail());
        message.setText(messageBody.toString());
        message.setSubject("Update Information for your booking");

        mailSender.send(message);
        System.out.println("Email notifikasi Sudah terkirim!");

    }
}
