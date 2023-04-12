package com.example.PayDay.model.responsemodel;

import com.example.PayDay.entity.Employee;
import com.example.PayDay.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.PayDay.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DailyEntryResponseModel {
    private Long deId;
    private Long deEmployeeId;
    private Long deProductId;
    private Long deProductQuantity;
    private Double deProductPerUnitCost;
    private Date deDate;
    private Employee employee;
    private Product product;
}
