package com.example.PayDay.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long userId;

    @Column
    private String userName;

    @Column
    private String userPhoneNumber;

    @Column
    private String userEmailId;

    @Column(length = 500)
    private String userAddress;

    @Column(length = 500)
    private String userCity;

    @Column(length = 500)
    private String userState;

    @Column(length =500)
    private String userPinCode;

}
