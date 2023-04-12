package com.example.PayDay.controller;

import com.example.PayDay.constant.IntegerConstant;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.requestmodel.ProductDepartmentRequestModel;
import com.example.PayDay.model.responsemodel.ProductDepartmentResponseModel;
import com.example.PayDay.service.ProductDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/productDepartment")
public class ProductDepartmentController {

    @Autowired
    private ProductDepartmentService productDepartmentService;

    @GetMapping
    public ResponseEntity<Object> getAllUserRoles() {
        List<ProductDepartmentResponseModel> productDepartmentResponseModelList = productDepartmentService.findAll();
        if (productDepartmentResponseModelList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Product Department Found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(productDepartmentResponseModelList)
                        .message("Fetched Product Department Lists")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping("/{pdId}")
    public ResponseEntity<Object> getProductDepartment(@PathVariable final Long pdId) {
        ProductDepartmentResponseModel productDepartmentResponseModel = productDepartmentService.get(pdId);
        if (productDepartmentResponseModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Product Department Found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(productDepartmentResponseModel)
                        .message("Fetched Product Department Lists")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
    @GetMapping("/pagination")
    public ResponseEntity<Object> getAllProductDepartmentsByPaginated(
            @RequestParam(required = false, defaultValue = "" + IntegerConstant.INT_ZERO) Integer pageNumber,
            @RequestParam(required = false, defaultValue = "" + IntegerConstant.INT_THIRTY) Integer pageSize) {
        return productDepartmentService.findAllPaginated(
                pageNumber, pageSize
        );
    }
    @PostMapping
    public ResponseEntity<Object> createProductDepartment(@RequestBody @Valid final ProductDepartmentRequestModel productDepartmentRequestModel) {
        ProductDepartmentResponseModel productDepartmentResponseModel = productDepartmentService.create(productDepartmentRequestModel);
        if (productDepartmentResponseModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Product Department Found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JsonResponse.builder()
                        .data(productDepartmentResponseModel)
                        .message("Fetched Product Department Lists")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

    @PutMapping("/{pdId}")
    public ResponseEntity<Object> updateProductDepartment(@PathVariable final Long pdId,
                                                          @RequestBody @Valid final ProductDepartmentRequestModel productDepartmentRequestModel) {
        ProductDepartmentResponseModel productDepartmentResponseModel = productDepartmentService.update(pdId, productDepartmentRequestModel);
        if (productDepartmentResponseModel == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Product Department Found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(productDepartmentResponseModel)
                        .message("Fetched Product Department Lists")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping("/{pdId}")
    public ResponseEntity<Object> deleteProductDepartment(@PathVariable final Long pdId) {
        productDepartmentService.delete(pdId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .message("Product Department Deleted Successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
