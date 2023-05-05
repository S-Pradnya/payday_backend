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
@Getter
@Setter
@SQLDelete(sql = "UPDATE product SET deleted = true WHERE product_id=?")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long productId;

    @Column
    private String productName;

    @Column
    public String productType;

    @Column
    private String productDescription;

    @Column
    private String productPerUnitCost;

    @Column
    private String productReturnCost;

    @Column(name="user_id")
    private Long productUserId;

    @Column
    private Boolean deleted = Boolean.FALSE;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User user;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<ProductDepartment> productDepartment;
}
