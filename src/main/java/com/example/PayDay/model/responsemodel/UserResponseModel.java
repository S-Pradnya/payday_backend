package com.example.PayDay.model.responsemodel;

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
public class UserResponseModel {

    private Long userId;
    @Size(max = 255)
    private String userName;
    @Size(max = 255)
    private String userPhoneNumber;
    @Size(max = 255)
    private String userEmailId;
    @Size(max = 500)
    private String userAddress;
    @Size(max = 500)
    private String userCity;
    @Size(max = 500)
    private String userState;
    @Size(max = 500)
    private String userPinCode;

}
