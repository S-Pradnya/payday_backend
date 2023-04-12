package com.example.PayDay.model.responsemodel;

import com.example.PayDay.entity.Department;
import com.example.PayDay.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductDepartmentResponseModel {

    private Long pdId;

    private Long pdProductId;

    private Long pdDepartmentId;
    private boolean deleted;
    @JsonIgnoreProperties({"productDepartment", "hibernateLazyInitializer", "handler"})
    private Product product;

    @JsonIgnoreProperties({"productDepartment", "hibernateLazyInitializer", "handler"})
    private Department department;

}
