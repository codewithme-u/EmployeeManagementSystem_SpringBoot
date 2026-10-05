package com.employeemanagementsystem.report.dto.response;

import com.employeemanagementsystem.employee.enums.EmployeeStatusEnums;


public record EmployeeStatusCountResponseDTO(
        EmployeeStatusEnums employeeStatus, Long count
) {

}