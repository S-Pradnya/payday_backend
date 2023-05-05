package com.example.PayDay.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SQLDelete(sql = "UPDATE department SET deleted = true WHERE department_id=?")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long departmentId;

    @Column
    private String departmentName;

    @Column(name = "user_id")
    private Long deUserId;

    @Column
    private Boolean deleted = Boolean.FALSE;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User user;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private Set<ProductDepartment> productDepartment;
}
