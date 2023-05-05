package com.example.PayDay.service;

import com.example.PayDay.model.requestmodel.DepartmentRequestModel;
import com.example.PayDay.model.responsemodel.DepartmentResponseModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    List<DepartmentResponseModel> findAll();

    Optional<DepartmentResponseModel> get(Long departmentId);

    DepartmentResponseModel create(DepartmentRequestModel departmentRequestModel);

    DepartmentResponseModel update(Long departmentId, DepartmentRequestModel departmentRequestModel);

    void delete(Long departmentId);

    ResponseEntity<Object> findAllPaginated(Integer pageNumber, Integer pageSize);
}
