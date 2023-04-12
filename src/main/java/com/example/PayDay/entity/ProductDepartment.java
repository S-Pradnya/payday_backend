package com.example.PayDay.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pdId;

    @Column(name="pd_product_id")
    private Long pdProductId;

    @Column(name="pd_department_id")
    private Long pdDepartmentId;

    @Column
    private Boolean deleted = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pd_product_id",insertable = false,updatable = false)
    @JsonIgnoreProperties("productDepartment")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pd_department_id",insertable = false,updatable = false)
    @JsonIgnoreProperties("productDepartment")
    private Department department;
}
