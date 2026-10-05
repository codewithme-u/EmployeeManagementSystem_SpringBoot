package com.employeemanagementsystem.employee.entity;

import com.employeemanagementsystem.department.entity.DepartmentEntity;
import com.employeemanagementsystem.employee.enums.EmployeeGenderEnums;
import com.employeemanagementsystem.employee.enums.EmployeeStatusEnums;
import com.employeemanagementsystem.employee.enums.EmployeeTypeEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "employee_name", nullable = false, length = 20)
    private String employeeName;

    @Column(name = "employee_email", nullable = false, length = 100, unique = true)
    private String employeeEmail;

    @Column(name = "employee_phone", nullable = false, length = 14)
    private String employeePhone;

    @Column(name = "employee_address", nullable = false, length = 50)
    private String employeeAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_gender", nullable = false, length = 10)
    private EmployeeGenderEnums employeeGenderEnums;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", nullable = false, length = 20)
    private EmployeeTypeEnums employeeTypeEnums;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentEntity departmentEntity;

    @Column(name = "base_salary", nullable = false)
    private Double baseSalary;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_status", nullable = false, length = 20)
    private EmployeeStatusEnums employeeStatusEnums;
}
