package com.example.PayDay.controller;

import com.example.PayDay.constant.IntegerConstant;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.requestmodel.AttendanceRequestModel;
import com.example.PayDay.model.requestmodel.DepartmentRequestModel;
import com.example.PayDay.model.responsemodel.AttendanceResponseModel;
import com.example.PayDay.model.responsemodel.DepartmentResponseModel;
import com.example.PayDay.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<Object> getAllAttendance() {
        List<AttendanceResponseModel> attendanceResponseModelList = attendanceService.findAll();
        if (attendanceResponseModelList.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Employee Attendance Found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());

        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(attendanceResponseModelList)
                        .message("Fetched Attendance Lists")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
    @GetMapping("/{attendanceId}")
    public ResponseEntity<Object> getAttendance(@PathVariable final Long attendanceId) {
        AttendanceResponseModel attendanceResponseModel = attendanceService.get(attendanceId);
        if (attendanceResponseModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Employee Attendance Found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(attendanceResponseModel)
                        .message("Fetched Attendance")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
    @GetMapping("/pagination")
    public ResponseEntity<Object> getAllProductsByPaginated(
            @RequestParam(required = false, defaultValue = "" + IntegerConstant.INT_ZERO) Integer pageNumber,
            @RequestParam(required = false, defaultValue = "" + IntegerConstant.INT_THIRTY) Integer pageSize) {
        return attendanceService.findAllPaginated(
                pageNumber, pageSize
        );
    }
    @PostMapping
    public ResponseEntity<Object> createAttendance(@RequestBody @Valid final AttendanceRequestModel attendanceRequestModel) {
        AttendanceResponseModel attendanceResponseModel  = attendanceService.create(attendanceRequestModel);
        if (attendanceResponseModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponse.builder()
                            .message("Something went wrong!!!")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JsonResponse.builder()
                        .data(attendanceResponseModel)
                        .message("Attendance Created Successfully")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }
    @PutMapping("/{attendanceId}")
    public ResponseEntity<Object> updateAttendance(@PathVariable final Long attendanceId,
                                                   @RequestBody @Valid final AttendanceRequestModel attendanceRequestModel) {
        AttendanceResponseModel attendanceResponseModel = attendanceService.update(attendanceId, attendanceRequestModel);
        if (attendanceResponseModel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(JsonResponse.builder()
                            .message("Something went wrong!!!")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(attendanceResponseModel)
                        .message("Attendance Updated Successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<Object> deleteAttendance(@PathVariable final Long attendanceId) {
        attendanceService.delete(attendanceId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .message("Attendance Deleted Successfully")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }
}
