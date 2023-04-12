package com.example.PayDay.model.responsemodel;

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
public class DepartmentResponseModel {

    private Long departmentId;
    @Size(max = 255)
    private String departmentName;
    private Long deUserId;
    private boolean deleted;
    private User user;
}
