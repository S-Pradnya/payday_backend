package com.example.PayDay.controller;

import com.example.PayDay.constant.IntegerConstant;
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

@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        List<UserResponseModel> userResponseModelList = userService.findAll();
        if (userResponseModelList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No User Found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());

        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(userResponseModelList)
                        .message("Fetched User Lists")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable final Long userId) {
        UserResponseModel userResponseModel = userService.get(userId);
        if (userResponseModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No User Found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(userResponseModel)
                        .message("Fetched User")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
    @GetMapping("/pagination")
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
                            .message("Something went wrong!!!")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JsonResponse.builder()
                        .data(userResponseModel)
                        .message("User Created Successfully")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable final Long userId,
                                             @RequestBody @Valid final UserRequestModel userRequestModel) {
        UserResponseModel userResponseModel = userService.update(userId, userRequestModel);
        if (userResponseModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponse.builder()
                            .message("Something went wrong!!!")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(userResponseModel)
                        .message("User Updated Successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable final Long userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .message("User Deleted Successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}

