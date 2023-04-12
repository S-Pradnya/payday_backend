package com.example.PayDay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DailyEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long deId;

    @Column(name = "de_employee_id")
    private Long deEmployeeId;

    @Column(name="de_product_id")
    private Long deProductId;
    @Column
    private Long deProductQuantity;

    @Column
    private Double deProductPerUnitCost;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date deDate = new Date();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "de_employee_id",insertable = false,updatable = false)
    private Employee employee;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "de_product_id",insertable = false,updatable = false)
    private Product product;

}
