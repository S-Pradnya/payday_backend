package com.example.PayDay.serviceclass;

import com.example.PayDay.entity.Product;
import com.example.PayDay.entity.ProductDepartment;
import com.example.PayDay.exception.ResourceNotFoundException;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.requestmodel.ProductRequestModel;
import com.example.PayDay.model.responsemodel.ProductDepartmentResponseModel;
import com.example.PayDay.model.responsemodel.ProductResponseModel;
import com.example.PayDay.repository.ProductRepository;
import com.example.PayDay.service.ProductService;
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
public class ProductServiceClass implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponseModel> findAll() {
        final List<Product> products = productRepository.findAll(Sort.by("productId"));
        return products.stream().map((product) -> mapToResponseModel(product, new ProductResponseModel())).collect(Collectors.toList());
    }

    public ProductResponseModel get(final Long productId) {
        return productRepository.findById(productId).map(product -> mapToResponseModel(product, new ProductResponseModel())).orElseThrow(() -> new ResourceNotFoundException());
    }

    public ProductResponseModel create(final ProductRequestModel productRequestModel) {
        final Product product = new Product();
        mapToEntity(productRequestModel, product);
        Product savedProduct = productRepository.save(product);
        return mapToResponseModel(product, new ProductResponseModel());
    }
    public ProductResponseModel update(final Long userId, final ProductRequestModel productRequestModel) {
        final Product product = productRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException());
        mapToEntity(productRequestModel, product);
        Product savedProduct = productRepository.save(product);
        return mapToResponseModel(product, new ProductResponseModel());
    }

    public void delete(final Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ResponseEntity<Object> findAllPaginated(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Product> products = productRepository.findAll(page);
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Products found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        List<ProductResponseModel> productResponseModelList = new ArrayList<>();
        for (Product product : products.getContent()) {
            productResponseModelList.add(mapToResponseModel(product, new ProductResponseModel()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(productResponseModelList)
                        .message("Products Paginated List.")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    private ProductResponseModel mapToResponseModel(final Product product, final ProductResponseModel productResponseModel) {
        productResponseModel.setProductId(product.getProductId());
        productResponseModel.setProductName(product.getProductName());
        productResponseModel.setProductType(product.getProductType());
        productResponseModel.setProductDescription(product.getProductDescription());
        productResponseModel.setProductPerUnitCost(product.getProductPerUnitCost());
        productResponseModel.setProductReturnCost(product.getProductReturnCost());
        productResponseModel.setProductUserId(product.getProductUserId());
        productResponseModel.setDeleted(product.getDeleted());
        productResponseModel.setUser(product.getUser());

        return productResponseModel;
    }
    private Product mapToEntity(final ProductRequestModel productRequestModel, final Product product) {
        product.setProductName(productRequestModel.getProductName());
        product.setProductType(productRequestModel.getProductType());
        product.setProductDescription(productRequestModel.getProductDescription());
        product.setProductPerUnitCost(productRequestModel.getProductPerUnitCost());
        product.setProductReturnCost(productRequestModel.getProductReturnCost());
        product.setProductUserId(productRequestModel.getProductUserId());
        product.setDeleted(productRequestModel.getDeleted());
        product.setUser(productRequestModel.getUser());

        return product;
    }
}
