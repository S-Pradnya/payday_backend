package com.example.PayDay.repository;

import com.example.PayDay.entity.ProductDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDepartmentRepository extends JpaRepository<ProductDepartment,Long> {
}
