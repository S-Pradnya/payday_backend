package com.example.PayDay.model.responsemodel;

import com.example.PayDay.entity.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AttendanceResponseModel {
    private Long attendanceId;
    private Long attendanceEmployeeId;
    private Date attendanceDate;
    private Boolean present;
    private Employee employee;
}
