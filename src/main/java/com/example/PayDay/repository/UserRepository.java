package com.example.PayDay.repository;

import com.example.PayDay.entity.User;
import com.example.PayDay.model.JsonResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository <User,Long> {

    ResponseEntity<JsonResponse> findByUserId(Long userId);

}
