package com.example.PayDay.serviceimpl;

import com.example.PayDay.constant.StringConstant;
import com.example.PayDay.entity.Department;
import com.example.PayDay.exception.ResourceNotFoundException;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.requestmodel.DepartmentRequestModel;
import com.example.PayDay.model.responsemodel.DepartmentResponseModel;
import com.example.PayDay.repository.DepartmentRepository;
import com.example.PayDay.service.DepartmentService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceClass implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<DepartmentResponseModel> findAll() {
        final List<Department> departments = departmentRepository.findAll(Sort.by("departmentId"));
        return departments.stream().map((department) -> mapToResponseModel(department, new DepartmentResponseModel())).collect(Collectors.toList());
    }

    public Optional<DepartmentResponseModel> get(final Long departmentId) {
        return departmentRepository.findById(departmentId).map(employee -> mapToResponseModel(employee, new DepartmentResponseModel()));
    }

    public DepartmentResponseModel create(final DepartmentRequestModel departmentRequestModel) {
        final Department department = new Department();
        mapToEntity(departmentRequestModel, department);
        Department savedDepartment = departmentRepository.save(department);
        return mapToResponseModel(department, new DepartmentResponseModel());
    }

    public DepartmentResponseModel update(final Long departmentId, final DepartmentRequestModel departmentRequestModel) {
        final Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new ResourceNotFoundException(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST));
        mapToEntity(departmentRequestModel, department);
        Department savedDepartment = departmentRepository.save(department);
        return mapToResponseModel(department, new DepartmentResponseModel());
    }

    public void delete(final Long departmentId) {departmentRepository.deleteById(departmentId);}

    @Override
    public ResponseEntity<Object> findAllPaginated(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Department> departments = departmentRepository.findAll(page);
        if (departments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        List<DepartmentResponseModel> departmentResponseModelList = new ArrayList<>();
        for (Department department : departments.getContent()) {
            departmentResponseModelList.add(mapToResponseModel(department, new DepartmentResponseModel()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(departmentResponseModelList)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_PAGINATION)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    private DepartmentResponseModel mapToResponseModel(final Department department, final DepartmentResponseModel departmentResponseModel) {
        departmentResponseModel.setDepartmentId(department.getDepartmentId());
        departmentResponseModel.setDepartmentName(department.getDepartmentName());
        departmentResponseModel.setDeUserId(department.getDeUserId());
        departmentResponseModel.setDeleted(department.getDeleted());
        departmentResponseModel.setUser(department.getUser());

        return departmentResponseModel;
    }
    private Department mapToEntity(final DepartmentRequestModel departmentRequestModel, final Department department) {
        department.setDepartmentName(departmentRequestModel.getDepartmentName());
        department.setDeUserId(departmentRequestModel.getDeUserId());
        department.setDeleted(departmentRequestModel.getDeleted());
        department.setUser(departmentRequestModel.getUser());

        return department;
    }

}
