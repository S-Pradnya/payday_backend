package com.example.PayDay.model.requestmodel;

import com.example.PayDay.entity.Department;
import com.example.PayDay.entity.Product;
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
public class ProductDepartmentRequestModel {

    private Long pdId;
    private Long pdProductId;
    private Long pdDepartmentId;
    private Boolean deleted = Boolean.FALSE;
    private Long product;
    private Long department;
}
