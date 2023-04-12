package com.example.PayDay.entity;

import com.example.PayDay.constant.StringConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long attendanceId;

    @Column(name="employee_id")
    private Long attendanceEmployeeId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id",insertable = false,updatable = false)
    private Employee employee;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = StringConstant.TEXT_DATE_FORMAT_YYYY_MM_DD)
    @Column
    private Date attendanceDate=new Date();

    @Column
    private Boolean present;

}
