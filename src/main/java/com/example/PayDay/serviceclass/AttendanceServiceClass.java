package com.example.PayDay.serviceclass;

import com.example.PayDay.entity.Attendance;
import com.example.PayDay.entity.ProductDepartment;
import com.example.PayDay.exception.ResourceNotFoundException;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.requestmodel.AttendanceRequestModel;
import com.example.PayDay.model.responsemodel.AttendanceResponseModel;
import com.example.PayDay.model.responsemodel.ProductDepartmentResponseModel;
import com.example.PayDay.repository.AttendanceRepository;
import com.example.PayDay.service.AttendanceService;
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
public class AttendanceServiceClass implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<AttendanceResponseModel> findAll() {
        final List<Attendance> attendances = attendanceRepository.findAll(Sort.by("attendanceId"));
        return attendances.stream().map((attendance) -> mapToResponseModel(attendance, new AttendanceResponseModel())).collect(Collectors.toList());
    }

    public AttendanceResponseModel get(final Long attendanceId) {
        return attendanceRepository.findById(attendanceId).map(attendance -> mapToResponseModel(attendance, new AttendanceResponseModel())).orElseThrow(() -> new ResourceNotFoundException());
    }

    public AttendanceResponseModel create(final AttendanceRequestModel attendanceRequestModel) {
        final Attendance attendance = new Attendance();
        mapToEntity(attendanceRequestModel, attendance);
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return mapToResponseModel(attendance, new AttendanceResponseModel());
    }

    public AttendanceResponseModel update(final Long attendanceId, final AttendanceRequestModel attendanceRequestModel) {
        final Attendance attendance = attendanceRepository.findById(attendanceId).orElseThrow(() -> new ResourceNotFoundException());
        mapToEntity(attendanceRequestModel, attendance);
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return mapToResponseModel(attendance, new AttendanceResponseModel());
    }
    public void delete(final Long attendanceId) {
        attendanceRepository.deleteById(attendanceId);
    }

    @Override
    public ResponseEntity<Object> findAllPaginated(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Attendance> attendances = attendanceRepository.findAll(page);
        if (attendances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message("No Attendance of Employees found")
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        List<AttendanceResponseModel> attendanceResponseModelList = new ArrayList<>();
        for (Attendance attendance : attendances.getContent()) {
            attendanceResponseModelList.add(mapToResponseModel(attendance, new AttendanceResponseModel()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(attendanceResponseModelList)
                        .message("Attendance Paginated List.")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    private AttendanceResponseModel mapToResponseModel(final Attendance attendance, final AttendanceResponseModel attendanceResponseModel) {
        attendanceResponseModel.setAttendanceId(attendance.getAttendanceId());
        attendanceResponseModel.setAttendanceEmployeeId(attendance.getAttendanceEmployeeId());
        attendanceResponseModel.setAttendanceDate(attendance.getAttendanceDate());
        attendanceResponseModel.setPresent(attendance.getPresent());
        attendanceResponseModel.setEmployee(attendance.getEmployee());

        return attendanceResponseModel;
    }
    private Attendance mapToEntity(final AttendanceRequestModel attendanceRequestModel, final Attendance attendance) {
        attendance.setAttendanceEmployeeId(attendanceRequestModel.getAttendanceEmployeeId());
        attendance.setAttendanceDate(attendanceRequestModel.getAttendanceDate());
        attendance.setPresent(attendanceRequestModel.getPresent());
        attendance.setEmployee(attendanceRequestModel.getEmployee());

        return attendance;
    }
}
