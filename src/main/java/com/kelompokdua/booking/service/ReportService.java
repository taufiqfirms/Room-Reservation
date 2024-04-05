package com.kelompokdua.booking.service;

import java.io.ByteArrayInputStream;
import java.util.Date;

public interface ReportService {
    ByteArrayInputStream generateDailyReport(Date startDate, Date endDate);
}
