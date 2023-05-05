package com.example.PayDay.serviceimpl;

import com.example.PayDay.constant.StringConstant;
import com.example.PayDay.entity.Employee;
import com.example.PayDay.exception.ResourceNotFoundException;
import com.example.PayDay.model.JsonResponse;
import com.example.PayDay.model.requestmodel.EmployeeRequestModel;
import com.example.PayDay.model.responsemodel.EmployeeResponseModel;
import com.example.PayDay.repository.EmployeeRepository;
import com.example.PayDay.service.EmployeeService;
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
public class EmployeeServiceClass implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeResponseModel> findAll() {
        final List<Employee> employees = employeeRepository.findAll(Sort.by("employeeId"));
        return employees.stream().map((employee) -> mapToResponseModel(employee, new EmployeeResponseModel())).collect(Collectors.toList());
    }

    public Optional<EmployeeResponseModel> get(final Long employeeId) {
        return employeeRepository.findById(employeeId).map(employee -> mapToResponseModel(employee, new EmployeeResponseModel()));
    }

    public EmployeeResponseModel create(final EmployeeRequestModel employeeRequestModel) {
        final Employee employee = new Employee();
        mapToEntity(employeeRequestModel, employee);
        Employee savedEmployee = employeeRepository.save(employee);
        return mapToResponseModel(employee, new EmployeeResponseModel());
    }

    public EmployeeResponseModel update(final Long employeeId, final EmployeeRequestModel employeeRequestModel) {
        final Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST));
        mapToEntity(employeeRequestModel, employee);
        Employee savedEmployee = employeeRepository.save(employee);
        return mapToResponseModel(employee, new EmployeeResponseModel());
    }

    public void delete(final Long employeeId) {employeeRepository.deleteById(employeeId);}

    @Override
    public ResponseEntity<Object> findAllPaginated(Integer pageNumber, Integer pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<Employee> employees = employeeRepository.findAll(page);
        if (employees.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(JsonResponse.builder()
                            .message(StringConstant.REQUEST_FAILURE_MESSAGE_BAD_REQUEST)
                            .status(HttpStatus.NOT_FOUND)
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        List<EmployeeResponseModel> employeeResponseModelList = new ArrayList<>();
        for (Employee employee : employees.getContent()) {
            employeeResponseModelList.add(mapToResponseModel(employee, new EmployeeResponseModel()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(JsonResponse.builder()
                        .data(employeeResponseModelList)
                        .message(StringConstant.REQUEST_SUCCESS_MESSAGE_PAGINATION)
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    private EmployeeResponseModel mapToResponseModel(final Employee employee, final EmployeeResponseModel employeeResponseModel) {
        employeeResponseModel.setEmployeeId(employee.getEmployeeId());
        employeeResponseModel.setEmployeeName(employee.getEmployeeName());
        employeeResponseModel.setEmployeePhoneNumber(employee.getEmployeePhoneNumber());
        employeeResponseModel.setEmployeeAddress(employee.getEmployeeAddress());
        employeeResponseModel.setEmployeeEmailId(employee.getEmployeeEmailId());
        employeeResponseModel.setEmployeeDepartmentId(employee.getEmployeeDepartmentId());
        employeeResponseModel.setEmployeeUserId(employee.getEmployeeUserId());
        employeeResponseModel.setDeleted(employee.getDeleted());
        employeeResponseModel.setDepartment(employee.getDepartment());
        employeeResponseModel.setUser(employee.getUser());

        return employeeResponseModel;
    }
    private Employee mapToEntity(final EmployeeRequestModel employeeRequestModel, final Employee employee) {
        employee.setEmployeeName(employeeRequestModel.getEmployeeName());
        employee.setEmployeePhoneNumber(employeeRequestModel.getEmployeePhoneNumber());
        employee.setEmployeeAddress(employeeRequestModel.getEmployeeAddress());
        employee.setEmployeeEmailId(employeeRequestModel.getEmployeeEmailId());
        employee.setEmployeeDepartmentId(employeeRequestModel.getEmployeeDepartmentId());
        employee.setEmployeeUserId(employeeRequestModel.getEmployeeUserId());
        employee.setDeleted(employeeRequestModel.getDeleted());
        employee.setDepartment(employeeRequestModel.getDepartment());
        employee.setUser(employeeRequestModel.getUser());

        return employee;
    }
}
