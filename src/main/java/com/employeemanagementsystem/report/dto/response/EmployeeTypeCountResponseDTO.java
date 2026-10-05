package com.employeemanagementsystem.report.dto.response;

import com.employeemanagementsystem.employee.enums.EmployeeTypeEnums;

public record EmployeeTypeCountResponseDTO(
        EmployeeTypeEnums employeeTypeEnums,
        Long count
) {

}
