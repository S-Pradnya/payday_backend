package com.example.PayDay.service;

import com.example.PayDay.model.requestmodel.ProductRequestModel;
import com.example.PayDay.model.requestmodel.UserRequestModel;
import com.example.PayDay.model.responsemodel.ProductResponseModel;
import com.example.PayDay.model.responsemodel.UserResponseModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    List<ProductResponseModel> findAll();

    ProductResponseModel get(Long productId);

    ProductResponseModel create(ProductRequestModel productRequestModel);

    ProductResponseModel update(Long productId, ProductRequestModel productRequestModel);

    void delete(Long productId);

    ResponseEntity<Object> findAllPaginated(Integer pageNumber, Integer pageSize);
}
