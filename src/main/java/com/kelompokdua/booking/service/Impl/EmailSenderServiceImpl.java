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
        String messageBody = "Dear " + roomBooking.getUser().getName() + ",\n\n" +
                "We would like to inform you about the recent update regarding your booking:\n\n" +
                "Booking ID: " + roomBooking.getId() + "\n" +
                "Room: " + roomBooking.getRoom().getName() + "\n" +
                "Check-in Date: " + roomBooking.getStartTime() + "\n" +
                "Check-out Date: " + roomBooking.getEndTime() + "\n" +
                "Status: " + roomBooking.getStatus() + "\n\n" +
                "Thank you for choosing our service. If you have any further questions or concerns, please don't hesitate to contact us.\n\n" +
                "Best regards,\n" +
                "Kelompok 2 Enigma Camp 2024";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kelompokduaenigma@gmail.com");
        message.setTo(roomBooking.getUser().getEmail());
        message.setText(messageBody);
        message.setSubject("Update Information for your booking");

        mailSender.send(message);
        System.out.println("Email notifikasi Sudah terkirim!");

    }

    @Override
    public void sendEmailCheckout(RoomBooking roomBooking) {
        String messageBody = "Dear " + roomBooking.getUser().getName() + ",\n\n" +
                "We would like to inform you that your booking has been successfully checked out:\n\n" +
                "Booking ID: " + roomBooking.getId() + "\n" +
                "Room: " + roomBooking.getRoom().getName() + "\n" +
                "Check-in Date: " + roomBooking.getStartTime() + "\n" +
                "Check-out Date: " + roomBooking.getEndTime() + "\n" +
                "Status: " + roomBooking.getStatus() + "\n\n" +
                "Thank you for choosing our service. If you have any further questions or concerns, please don't hesitate to contact us.\n\n" +
                "Best regards,\n" +
                "Kelompok 2 Enigma Camp 2024";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kelompokduaenigma@gmail.com");
        message.setTo(roomBooking.getUser().getEmail());
        message.setText(messageBody);
        message.setSubject("Update: Your booking has been checked out successfully");

        mailSender.send(message);
        System.out.println("Notification email has been sent to " + roomBooking.getUser().getEmail() + " for successful checkout!");

    }

}
