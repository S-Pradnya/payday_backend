package com.example.PayDay.controller;

import com.example.PayDay.constant.ApiConstant;
import com.example.PayDay.constant.IntegerConstant;
import com.example.PayDay.constant.StringConstant;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.requestmodel.UserRequestModel;
import com.example.PayDay.model.responsemodel.UserResponseModel;
import com.example.PayDay.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = ApiConstant.REQUEST_MAPPING_KEY_USER)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        List<UserResponseModel> userResponseModelList = userService.findAll();
        if (userResponseModelList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(userResponseModelList)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_USER_FETCHED)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping(value = ApiConstant.REQUEST_MAPPING_USER_ID)
    public ResponseEntity<Object> getProduct(@PathVariable final Long userId) {
        Optional<UserResponseModel> userResponseModel = userService.get(userId);
        if (userResponseModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponse.builder()
                            .data(userResponseModel)
                            .message(StringConstant.REQUEST_SUCCESS_MESSAGE_SELECTED_USER_FETCHED +userId)
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(JsonResponse.builder()
                        .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build());

    }
    @GetMapping(ApiConstant.REQUEST_MAPPING_KEY_PAGINATION)
    public ResponseEntity<Object> getAllUsersByPaginated(
            @RequestParam(required = false, defaultValue = "" + IntegerConstant.INT_ZERO) Integer pageNumber,
            @RequestParam(required = false, defaultValue = "" + IntegerConstant.INT_THIRTY) Integer pageSize) {
        return userService.findAllPaginated(
                pageNumber, pageSize
        );
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid final UserRequestModel userRequestModel) {
        UserResponseModel userResponseModel = userService.create(userRequestModel);
        if (userResponseModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JsonResponse.builder()
                        .data(userResponseModel)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_USER_CREATED)
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

    @PutMapping(ApiConstant.REQUEST_MAPPING_USER_ID)
    public ResponseEntity<Object> updateUser(@PathVariable final Long userId,
                                             @RequestBody @Valid final UserRequestModel userRequestModel) {
        UserResponseModel userResponseModel = userService.update(userId, userRequestModel);
        if (userResponseModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(userResponseModel)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_USER_UPDATED)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping(ApiConstant.REQUEST_MAPPING_USER_ID)
    public ResponseEntity<Object> deleteUser(@PathVariable final Long userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_USER_DELETED)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}

