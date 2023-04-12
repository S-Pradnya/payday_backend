package com.example.PayDay.model.responsemodel;

import com.example.PayDay.entity.Department;
import com.example.PayDay.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmployeeResponseModel {

    private Long employeeId;
    @Size(max = 255)
    private String employeeName;
    @Size(max = 255)
    private String employeePhoneNumber;
    @Size(max = 500)
    private String employeeAddress;
    @Size(max = 255)
    private String employeeEmailId;
    private Long employeeDepartmentId;
    private Long employeeUserId;
    private boolean deleted;
    private Department department;
    private User user;

}
