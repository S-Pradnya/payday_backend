package com.example.PayDay.model.requestmodel;

import com.example.PayDay.entity.Employee;
import com.example.PayDay.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DailyEntryRequestModel {
    private Long deId;
    private Long deEmployeeId;
    private Long deProductId;
    private Long deProductQuantity;
    private Double deProductPerUnitCost;
    private Date deDate;
    private Employee employee;
    private Product product;

}
