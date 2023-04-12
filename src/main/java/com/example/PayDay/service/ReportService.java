package com.example.PayDay.service;

import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface ReportService {

    ResponseEntity<Object> getAdminReports(Integer pageNumber, Integer pageSize, Date deDate, Date endDate, Long deEmployeeId);
}
