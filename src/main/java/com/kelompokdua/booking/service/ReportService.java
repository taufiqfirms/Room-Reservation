package com.kelompokdua.booking.service;

import java.io.ByteArrayInputStream;
import java.util.Date;

public interface ReportService {
    ByteArrayInputStream generateBookingReport(Date startDate, Date endDate);
    ByteArrayInputStream generateRoomReport();
    ByteArrayInputStream generateEquipmentReport();
    ByteArrayInputStream generateUserReport();
}
