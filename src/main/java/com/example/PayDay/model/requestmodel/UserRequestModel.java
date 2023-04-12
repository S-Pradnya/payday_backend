package com.example.PayDay.model.requestmodel;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class UserRequestModel {

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
