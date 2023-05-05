package com.example.PayDay.controller;

import com.example.PayDay.constant.ApiConstant;
import com.example.PayDay.constant.IntegerConstant;
import com.example.PayDay.constant.StringConstant;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.requestmodel.ProductRequestModel;
import com.example.PayDay.model.responsemodel.ProductResponseModel;
import com.example.PayDay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = ApiConstant.REQUEST_MAPPING_KEY_PRODUCT)
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Object> getAllProducts() {
        List<ProductResponseModel> productResponseModelList = productService.findAll();
        if (productResponseModelList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(productResponseModelList)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_PRODUCT_FETCHED)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
    @GetMapping(ApiConstant.REQUEST_MAPPING_PRODUCT_ID)
    public ResponseEntity<Object> getProduct(@PathVariable final Long productId) {
        Optional<ProductResponseModel> productResponseModel = productService.get(productId);
        if (productResponseModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponse.builder()
                            .data(productResponseModel)
                            .message(StringConstant.REQUEST_SUCCESS_MESSAGE_SELECTED_PRODUCT_FETCHED +productId)
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(JsonResponse.builder()
                        .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build());
    }
    @GetMapping(ApiConstant.REQUEST_MAPPING_KEY_PAGINATION)
    public ResponseEntity<Object> getAllProductsByPaginated(
            @RequestParam(required = false, defaultValue = "" + IntegerConstant.INT_ZERO) Integer pageNumber,
            @RequestParam(required = false, defaultValue = "" + IntegerConstant.INT_THIRTY) Integer pageSize) {
        return productService.findAllPaginated(
                pageNumber, pageSize
        );
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody @Valid final ProductRequestModel productRequestModel) {
        ProductResponseModel productResponseModel = productService.create(productRequestModel);
        if (productResponseModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JsonResponse.builder()
                        .data(productResponseModel)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_PRODUCT_CREATED)
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }
    @PutMapping(ApiConstant.REQUEST_MAPPING_PRODUCT_ID)
    public ResponseEntity<Object> updateProduct(@PathVariable final Long productId,
                                             @RequestBody @Valid final ProductRequestModel productRequestModel) {
        ProductResponseModel productResponseModel = productService.update(productId, productRequestModel);
        if (productResponseModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(productResponseModel)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_PRODUCT_UPDATED)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
    @DeleteMapping(ApiConstant.REQUEST_MAPPING_PRODUCT_ID)
    public ResponseEntity<Object> deleteProduct(@PathVariable final Long productId) {
        productService.delete(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_PRODUCT_DELETED)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
