package com.example.PayDay.controller;

import com.example.PayDay.constant.IntegerConstant;
import com.example.PayDay.constant.StringConstant;
import com.example.PayDay.repository.DailyEntryRepository;
import com.example.PayDay.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/reports")
public class ReportController {

    @Autowired
    ReportService reportService;
    @Autowired
    private DailyEntryRepository dailyEntryRepository;

    @GetMapping("/dailyEntry")
    public ResponseEntity<Object> getData(
            @RequestParam(required = false, defaultValue = "" + IntegerConstant.INT_ZERO) Integer pageNumber,
            @RequestParam(required = false, defaultValue = "" + IntegerConstant.INT_THIRTY) Integer pageSize,
            @DateTimeFormat(pattern = StringConstant.TEXT_DATE_FORMAT_YYYY_MM_DD)
            @RequestParam(required = false) Date deDate,
            @DateTimeFormat(pattern = StringConstant.TEXT_DATE_FORMAT_YYYY_MM_DD)
            @RequestParam(required = false) Date endDate,
            @RequestParam(required = false) Long deEmployeeId
    )
    {
        return reportService.getAdminReports(pageNumber, pageSize,deDate,endDate,deEmployeeId);
    }
}
