package com.example.PayDay.serviceclass;

import com.example.PayDay.entity.ProductDepartment;
import com.example.PayDay.entity.Product;
import com.example.PayDay.entity.Department;
import com.example.PayDay.exception.ResourceNotFoundException;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.requestmodel.ProductDepartmentRequestModel;
import com.example.PayDay.model.responsemodel.ProductDepartmentResponseModel;
import com.example.PayDay.repository.DepartmentRepository;
import com.example.PayDay.repository.ProductDepartmentRepository;
import com.example.PayDay.repository.ProductRepository;
import com.example.PayDay.service.ProductDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductDepartmentServiceClass implements ProductDepartmentService {

    @Autowired
    private ProductDepartmentRepository productDepartmentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<ProductDepartmentResponseModel> findAll() {
        final List<ProductDepartment> productDepartments = productDepartmentRepository.findAll(Sort.by("pdId"));
        return productDepartments.stream()
                .map((productDepartment) -> mapToResponseModel(productDepartment, new ProductDepartmentResponseModel()))
                .collect(Collectors.toList());
    }

    public ProductDepartmentResponseModel get(final Long pdId) {
        return productDepartmentRepository.findById(pdId)
                .map(productDepartment -> mapToResponseModel(productDepartment, new ProductDepartmentResponseModel()))
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    public ProductDepartmentResponseModel create(final ProductDepartmentRequestModel productDepartmentRequestModel) {
        final ProductDepartment productDepartment = new ProductDepartment();
        mapToEntity(productDepartmentRequestModel, productDepartment);
        ProductDepartment savedProductDepartment = productDepartmentRepository.save(productDepartment);
        return mapToResponseModel(productDepartment, new ProductDepartmentResponseModel());
    }

    public ProductDepartmentResponseModel update(final Long pdId, final ProductDepartmentRequestModel productDepartmentRequestModel) {
        final ProductDepartment productDepartment = productDepartmentRepository.findById(pdId)
                .orElseThrow(() -> new ResourceNotFoundException());
        mapToEntity(productDepartmentRequestModel, productDepartment);
        ProductDepartment savedProductDepartment = productDepartmentRepository.save(productDepartment);
        return mapToResponseModel(productDepartment, new ProductDepartmentResponseModel());
    }

    public void delete(final Long pdId) {
        productDepartmentRepository.deleteById(pdId);
    }

    @Override
    public ResponseEntity<Object> findAllPaginated(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<ProductDepartment> productDepartments = productDepartmentRepository.findAll(page);
        if (productDepartments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No ProductDepartments found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        List<ProductDepartmentResponseModel> productDepartmentResponseModelList = new ArrayList<>();
        for (ProductDepartment productDepartment : productDepartments.getContent()) {
            productDepartmentResponseModelList.add(mapToResponseModel(productDepartment, new ProductDepartmentResponseModel()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(productDepartmentResponseModelList)
                        .message("ProductDepartments Paginated List.")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    private ProductDepartmentResponseModel mapToResponseModel(final ProductDepartment productDepartment,
                                                        final ProductDepartmentResponseModel productDepartmentResponseModel) {
        productDepartmentResponseModel.setPdId(productDepartment.getPdId());
        productDepartmentResponseModel.setPdProductId(productDepartment.getPdProductId());
        productDepartmentResponseModel.setPdDepartmentId(productDepartment.getPdDepartmentId());
        productDepartmentResponseModel.setDeleted(productDepartment.getDeleted());
        productDepartmentResponseModel.setProduct(productDepartment.getProduct() == null ? null : productDepartment.getProduct());
        productDepartmentResponseModel.setDepartment(productDepartment.getDepartment() == null ? null : productDepartment.getDepartment());
        return productDepartmentResponseModel;
    }

    private ProductDepartment mapToEntity(final ProductDepartmentRequestModel productDepartmentRequestModel,
                                    final ProductDepartment productDepartment) {
        productDepartment.setPdProductId(productDepartmentRequestModel.getPdProductId());
        productDepartment.setPdDepartmentId(productDepartmentRequestModel.getPdDepartmentId());
        productDepartment.setDeleted(productDepartmentRequestModel.getDeleted());
        final Product product = productDepartmentRequestModel.getProduct() == null ? null : productRepository.findById(productDepartmentRequestModel.getProduct())
                .orElseThrow(() -> new ResourceNotFoundException());
        productDepartment.setProduct(product);
        final Department department = productDepartmentRequestModel.getDepartment() == null ? null : departmentRepository.findById(productDepartmentRequestModel.getDepartment())
                .orElseThrow(() -> new ResourceNotFoundException());
        productDepartment.setDepartment(department);
        return productDepartment;
    }

}
