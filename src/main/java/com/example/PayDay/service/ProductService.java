package com.example.PayDay.service;

import com.example.PayDay.model.requestmodel.ProductRequestModel;
import com.example.PayDay.model.responsemodel.ProductResponseModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductResponseModel> findAll();

    Optional<ProductResponseModel> get(Long productId);

    ProductResponseModel create(ProductRequestModel productRequestModel);

    ProductResponseModel update(Long productId, ProductRequestModel productRequestModel);

    void delete(Long productId);

    ResponseEntity<Object> findAllPaginated(Integer pageNumber, Integer pageSize);
}
