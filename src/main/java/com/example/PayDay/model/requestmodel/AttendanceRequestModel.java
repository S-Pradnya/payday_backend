package com.example.PayDay.model.requestmodel;

import com.example.PayDay.entity.Employee;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AttendanceRequestModel {

    private Long attendanceId;
    private Long attendanceEmployeeId;
    private Date attendanceDate;
    private Boolean present;
    private Employee employee;
}
