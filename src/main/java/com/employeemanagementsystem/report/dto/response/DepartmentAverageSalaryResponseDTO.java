package com.employeemanagementsystem.report.dto.response;

public record DepartmentAverageSalaryResponseDTO(
        Long departmentId,
        String departmentName,
        Double averageSalary
) {
}