package com.example.PayDay.service;


import com.example.PayDay.model.requestmodel.UserRequestModel;
import com.example.PayDay.model.responsemodel.UserResponseModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


public interface UserService {
    List<UserResponseModel> findAll();

    UserResponseModel create(UserRequestModel userRequestModel);

    UserResponseModel update(Long userId, UserRequestModel userRequestModel);

    void delete(Long userId);

    ResponseEntity<Object> findAllPaginated(Integer pageNumber, Integer pageSize);

    Optional<UserResponseModel> get(Long userId);
}
