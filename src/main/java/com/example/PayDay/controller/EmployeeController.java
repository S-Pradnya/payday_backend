package com.example.PayDay.controller;

import com.example.PayDay.constant.ApiConstant;
import com.example.PayDay.constant.IntegerConstant;
import com.example.PayDay.constant.StringConstant;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.requestmodel.EmployeeRequestModel;
import com.example.PayDay.model.responsemodel.EmployeeResponseModel;
import com.example.PayDay.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = ApiConstant.REQUEST_MAPPING_KEY_EMPLOYEE)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Object> getAllEmployees() {
        List<EmployeeResponseModel> employeeResponseModelList = employeeService.findAll();
        if (employeeResponseModelList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(employeeResponseModelList)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_EMPLOYEE_FETCHED)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
    @GetMapping(ApiConstant.REQUEST_MAPPING_EMPLOYEE_ID)
    public ResponseEntity<Object> getEmployee(@PathVariable final Long employeeId) {
        Optional<EmployeeResponseModel> employeeResponseModel = employeeService.get(employeeId);
        if (employeeResponseModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponse.builder()
                            .data(employeeResponseModel)
                            .message(StringConstant.REQUEST_SUCCESS_MESSAGE_SELECTED_EMPLOYEE_FETCHED +employeeId)
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
    public ResponseEntity<Object> getAllEmployeesByPaginated(
            @RequestParam(required = false, defaultValue = "" + IntegerConstant.INT_ZERO) Integer pageNumber,
            @RequestParam(required = false, defaultValue = "" + IntegerConstant.INT_THIRTY) Integer pageSize) {
        return employeeService.findAllPaginated(
                pageNumber, pageSize
        );
    }
    @PostMapping
    public ResponseEntity<Object> createEmployee(@RequestBody @Valid final EmployeeRequestModel employeeRequestModel) {
        EmployeeResponseModel employeeResponseModel = employeeService.create(employeeRequestModel);
        if (employeeResponseModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JsonResponse.builder()
                        .data(employeeResponseModel)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_EMPLOYEE_CREATED)
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }
    @PutMapping(ApiConstant.REQUEST_MAPPING_EMPLOYEE_ID)
    public ResponseEntity<Object> updateEmployee(@PathVariable final Long employeeId,
                                                @RequestBody @Valid final EmployeeRequestModel employeeRequestModel) {
        EmployeeResponseModel employeeResponseModel = employeeService.update(employeeId, employeeRequestModel);
        if (employeeResponseModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(employeeResponseModel)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_EMPLOYEE_UPDATED)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
    @DeleteMapping(ApiConstant.REQUEST_MAPPING_EMPLOYEE_ID)
    public ResponseEntity<Object> deleteEmployee(@PathVariable final Long employeeId) {
            employeeService.delete(employeeId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_SUCCESS_MESSAGE_EMPLOYEE_DELETED)
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());
    }
}
