package com.example.PayDay.service;

import com.example.PayDay.model.requestmodel.EmployeeRequestModel;
import com.example.PayDay.model.responsemodel.EmployeeResponseModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {

    List<EmployeeResponseModel> findAll();

    EmployeeResponseModel get(Long employeeId);

    EmployeeResponseModel create(EmployeeRequestModel employeeRequestModel);

    EmployeeResponseModel update(Long employeeId, EmployeeRequestModel employeeRequestModel);

    void delete(Long employeeId);

    ResponseEntity<Object> findAllPaginated(Integer pageNumber, Integer pageSize);
}
