package com.example.PayDay.controller;

import com.example.PayDay.constant.ApiConstant;
import com.example.PayDay.constant.IntegerConstant;
import com.example.PayDay.constant.StringConstant;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.requestmodel.DepartmentRequestModel;
import com.example.PayDay.model.responsemodel.DepartmentResponseModel;
import com.example.PayDay.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = ApiConstant.REQUEST_MAPPING_KEY_DEPARTMENT)
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<Object> getAllDepartments() {
        List<DepartmentResponseModel> departmentResponseModelList = departmentService.findAll();
        if (departmentResponseModelList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(departmentResponseModelList)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_DEPARTMENT_FETCHED)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping(ApiConstant.REQUEST_MAPPING_DEPARTMENT_ID)
    public ResponseEntity<Object> getDepartment(@PathVariable final Long departmentId) {
        Optional<DepartmentResponseModel> departmentResponseModel = departmentService.get(departmentId);
        if (departmentResponseModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponse.builder()
                            .data(departmentResponseModel)
                            .message(StringConstant.REQUEST_SUCCESS_MESSAGE_SELECTED_DEPARTMENT_FETCHED +departmentId)
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
    public ResponseEntity<Object> getAllDepartmentsByPaginated(
            @RequestParam(required = false, defaultValue = "" + IntegerConstant.INT_ZERO) Integer pageNumber,
            @RequestParam(required = false, defaultValue = "" + IntegerConstant.INT_THIRTY) Integer pageSize) {
        return departmentService.findAllPaginated(
                pageNumber, pageSize
        );
    }
    @PostMapping
    public ResponseEntity<Object> createDepartment(@RequestBody @Valid final DepartmentRequestModel departmentRequestModel) {
        DepartmentResponseModel departmentResponseModel  = departmentService.create(departmentRequestModel);
        if (departmentResponseModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JsonResponse.builder()
                        .data(departmentResponseModel)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_DEPARTMENT_CREATED)
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

    @PutMapping(ApiConstant.REQUEST_MAPPING_DEPARTMENT_ID)
    public ResponseEntity<Object> updateDepartment(@PathVariable final Long departmentId,
                                                @RequestBody @Valid final DepartmentRequestModel departmentRequestModel) {
        DepartmentResponseModel departmentResponseModel = departmentService.update(departmentId, departmentRequestModel);
        if (departmentResponseModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(departmentResponseModel)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_DEPARTMENT_UPDATED)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping(ApiConstant.REQUEST_MAPPING_DEPARTMENT_ID)
    public ResponseEntity<Object> deleteDepartment(@PathVariable final Long departmentId) {
            departmentService.delete(departmentId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_SUCCESS_MESSAGE_DEPARTMENT_DELETED)
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());
    }
}
