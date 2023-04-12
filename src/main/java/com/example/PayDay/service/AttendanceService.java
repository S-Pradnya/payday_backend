package com.example.PayDay.service;

import com.example.PayDay.model.requestmodel.AttendanceRequestModel;
import com.example.PayDay.model.requestmodel.DepartmentRequestModel;
import com.example.PayDay.model.responsemodel.AttendanceResponseModel;
import com.example.PayDay.model.responsemodel.DepartmentResponseModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AttendanceService {

    List<AttendanceResponseModel> findAll();

    AttendanceResponseModel get(Long attendanceId);

    AttendanceResponseModel create(AttendanceRequestModel attendanceRequestModel);

    AttendanceResponseModel update(Long attendanceId, AttendanceRequestModel attendanceRequestModel);

    void delete(Long attendanceId);

    ResponseEntity<Object> findAllPaginated(Integer pageNumber, Integer pageSize);
}
