package com.employeemanagementsystem.report.dto.response;

public record DepartmentTotalSalaryResponseDTO(
        Long departmentId,
        String departmentName,
        Double totalSalary
) {
}