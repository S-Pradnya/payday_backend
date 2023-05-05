package com.example.PayDay.service;

import com.example.PayDay.model.requestmodel.ProductDepartmentRequestModel;
import com.example.PayDay.model.responsemodel.ProductDepartmentResponseModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProductDepartmentService {

    List<ProductDepartmentResponseModel> findAll();

    Optional<ProductDepartmentResponseModel> get(Long pdId);

    ProductDepartmentResponseModel create(ProductDepartmentRequestModel productDepartmentRequestModel);

    ProductDepartmentResponseModel update(Long pdId, ProductDepartmentRequestModel productDepartmentRequestModel);

    void delete(Long pdId);

    ResponseEntity<Object> findAllPaginated(Integer pageNumber, Integer pageSize);
}
