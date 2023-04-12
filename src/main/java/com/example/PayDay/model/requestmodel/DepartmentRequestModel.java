package com.example.PayDay.model.requestmodel;

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
public class DepartmentRequestModel {
    private Long departmentId;
    @Size(max = 255)
    private String departmentName;
    private Long deUserId;
    private Boolean deleted = Boolean.FALSE;
    private User user;
}
