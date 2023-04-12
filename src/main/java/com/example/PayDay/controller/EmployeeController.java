package com.example.PayDay.controller;

import com.example.PayDay.constant.IntegerConstant;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.requestmodel.EmployeeRequestModel;
import com.example.PayDay.model.requestmodel.ProductRequestModel;
import com.example.PayDay.model.responsemodel.EmployeeResponseModel;
import com.example.PayDay.model.responsemodel.ProductResponseModel;
import com.example.PayDay.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Object> getAllEmployees() {
        List<EmployeeResponseModel> employeeResponseModelList = employeeService.findAll();
        if (employeeResponseModelList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Employee Found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());

        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(employeeResponseModelList)
                        .message("Fetched Employee Lists")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
    @GetMapping("/{employeeId}")
    public ResponseEntity<Object> getEmployee(@PathVariable final Long employeeId) {
        EmployeeResponseModel employeeResponseModel = employeeService.get(employeeId);
        if (employeeResponseModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Employee Found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(employeeResponseModel)
                        .message("Fetched Employee")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
    @GetMapping("/pagination")
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
                            .message("Something went wrong!!!")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JsonResponse.builder()
                        .data(employeeResponseModel)
                        .message("Employee Created Successfully")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }
    @PutMapping("/{employeeId}")
    public ResponseEntity<Object> updateEmployee(@PathVariable final Long employeeId,
                                                @RequestBody @Valid final EmployeeRequestModel employeeRequestModel) {
        EmployeeResponseModel employeeResponseModel = employeeService.update(employeeId, employeeRequestModel);
        if (employeeResponseModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponse.builder()
                            .message("Something went wrong!!!")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(employeeResponseModel)
                        .message("Employee Updated Successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable final Long employeeId) {
        employeeService.delete(employeeId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .message("Employee Deleted Successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
