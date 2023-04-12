package com.example.PayDay.serviceclass;

import com.example.PayDay.entity.ProductDepartment;
import com.example.PayDay.entity.User;
import com.example.PayDay.exception.ResourceNotFoundException;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.requestmodel.UserRequestModel;
import com.example.PayDay.model.responsemodel.ProductDepartmentResponseModel;
import com.example.PayDay.model.responsemodel.UserResponseModel;
import com.example.PayDay.repository.UserRepository;
import com.example.PayDay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceClass implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponseModel> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("userId"));
        return users.stream().map((user) -> mapToResponseModel(user, new UserResponseModel())).collect(Collectors.toList());
    }

    public UserResponseModel get(final Long userId) {
        return userRepository.findById(userId).map(user -> mapToResponseModel(user, new UserResponseModel())).orElseThrow(() -> new ResourceNotFoundException());
    }

    public UserResponseModel create(final UserRequestModel userRequestModel) {
        final User user = new User();
        mapToEntity(userRequestModel, user);
        User savedUser = userRepository.save(user);
        return mapToResponseModel(user, new UserResponseModel());
    }

    public UserResponseModel update(final Long userId, final UserRequestModel userRequestModel) {
        final User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException());
        mapToEntity(userRequestModel, user);
        User savedUser = userRepository.save(user);
        return mapToResponseModel(user, new UserResponseModel());
    }

    public void delete(final Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public ResponseEntity<Object> findAllPaginated(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<User> users = userRepository.findAll(page);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Users found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        List<UserResponseModel> userResponseModelList = new ArrayList<>();
        for (User user : users.getContent()) {
            userResponseModelList.add(mapToResponseModel(user, new UserResponseModel()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(userResponseModelList)
                        .message("Users Paginated List.")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    private UserResponseModel mapToResponseModel(final User user, final UserResponseModel userResponseModel) {
        userResponseModel.setUserId(user.getUserId());
        userResponseModel.setUserName(user.getUserName());
        userResponseModel.setUserPhoneNumber(user.getUserPhoneNumber());
        userResponseModel.setUserEmailId(user.getUserEmailId());
        userResponseModel.setUserAddress(user.getUserAddress());
        userResponseModel.setUserCity(user.getUserCity());
        userResponseModel.setUserState(user.getUserState());
        userResponseModel.setUserPinCode(user.getUserPinCode());

        return userResponseModel;
    }

    private User mapToEntity(final UserRequestModel userRequestModel, final User user) {
        user.setUserName(userRequestModel.getUserName());
        user.setUserPhoneNumber(userRequestModel.getUserPhoneNumber());
        user.setUserEmailId(userRequestModel.getUserEmailId());
        user.setUserAddress(userRequestModel.getUserAddress());
        user.setUserCity(userRequestModel.getUserCity());
        user.setUserState(userRequestModel.getUserState());
        user.setUserPinCode(userRequestModel.getUserPinCode());

        return user;
    }
}

