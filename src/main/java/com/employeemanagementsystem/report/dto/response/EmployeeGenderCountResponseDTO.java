package com.employeemanagementsystem.report.dto.response;

import com.employeemanagementsystem.employee.enums.EmployeeGenderEnums;

public record EmployeeGenderCountResponseDTO(EmployeeGenderEnums employeeGenderEnums, Long count) {
}
