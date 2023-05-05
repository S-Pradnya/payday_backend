package com.example.PayDay.model.requestmodel;

import com.example.PayDay.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductRequestModel {

    private Long productId;
    @Size(max = 255)
    private String productName;
    @Size(max = 255)
    private String productType;
    @Size(max = 255)
    private String productDescription;
    @Size(max = 500)
    private String productPerUnitCost;
    @Size(max = 500)
    private String productReturnCost;
    private Long productUserId;
    private Boolean deleted = Boolean.FALSE;
    private User user;
}
