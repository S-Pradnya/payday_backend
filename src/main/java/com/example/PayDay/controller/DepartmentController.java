package com.example.PayDay.controller;

import com.example.PayDay.constant.IntegerConstant;
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

@Controller
@RequestMapping(value = "/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<Object> getAllDepartments() {
        List<DepartmentResponseModel> departmentResponseModelList = departmentService.findAll();
        if (departmentResponseModelList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Department Found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());

        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(departmentResponseModelList)
                        .message("Fetched Department Lists")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<Object> getDepartment(@PathVariable final Long departmentId) {
        DepartmentResponseModel departmentResponseModel = departmentService.get(departmentId);
        if (departmentResponseModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Department Found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(departmentResponseModel)
                        .message("Fetched Department")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
    @GetMapping("/pagination")
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
                            .message("Something went wrong!!!")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JsonResponse.builder()
                        .data(departmentResponseModel)
                        .message("Department Created Successfully")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<Object> updateDepartment(@PathVariable final Long departmentId,
                                                @RequestBody @Valid final DepartmentRequestModel departmentRequestModel) {
        DepartmentResponseModel departmentResponseModel = departmentService.update(departmentId, departmentRequestModel);
        if (departmentResponseModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponse.builder()
                            .message("Something went wrong!!!")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(departmentResponseModel)
                        .message("Department Updated Successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable final Long departmentId) {
        departmentService.delete(departmentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .message("Department Deleted Successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
