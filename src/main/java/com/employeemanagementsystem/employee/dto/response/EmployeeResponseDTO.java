package com.employeemanagementsystem.employee.dto.response;

import com.employeemanagementsystem.employee.enums.EmployeeGenderEnums;
import com.employeemanagementsystem.employee.enums.EmployeeStatusEnums;
import com.employeemanagementsystem.employee.enums.EmployeeTypeEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {

    private Long employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeePhone;
    private String employeeAddress;
    private EmployeeGenderEnums employeeGenderEnums;
    private LocalDate dateOfBirth;
    private LocalDate hireDate;
    private EmployeeTypeEnums employeeTypeEnums;
    private Long departmentId;
    private String departmentName;
    private Double baseSalary;
    private EmployeeStatusEnums employeeStatusEnums;
}
