package com.example.PayDay.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long employeeId;

    @Column
    private String employeeName;

    @Column
    private String employeePhoneNumber;

    @Column(length = 500)
    private String employeeAddress;

    @Column
    private String employeeEmailId;

    @Column(name="employee_department_id")
    private Long employeeDepartmentId;

    @Column(name="user_id")
    private Long employeeUserId;

    @Column
    private Boolean deleted = Boolean.FALSE;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_department_id",insertable = false,updatable = false)
    private Department department;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User user;
}
