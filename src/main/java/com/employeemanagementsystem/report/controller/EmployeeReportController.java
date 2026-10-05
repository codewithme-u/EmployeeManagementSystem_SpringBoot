package com.employeemanagementsystem.report.controller;

import com.employeemanagementsystem.annotation.EmployeeManagementRestController;
import com.employeemanagementsystem.report.dto.response.EmployeeGenderCountResponseDTO;
import com.employeemanagementsystem.report.dto.response.EmployeeStatusCountResponseDTO;
import com.employeemanagementsystem.report.dto.response.EmployeeTypeCountResponseDTO;
import com.employeemanagementsystem.report.service.EmployeeReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(
        name = "Employee Reports",
        description = "Employee reporting APIs"
)

@EmployeeManagementRestController
public class EmployeeReportController {

    private final EmployeeReportService employeeReportService;

    public EmployeeReportController(EmployeeReportService employeeReportService) {
        this.employeeReportService = employeeReportService;
    }

    @Operation(
            summary = "Get employee count by status",
            description = "Returns the number of employees grouped by employee status."
    )
    @GetMapping("/reports/employees/by-status")
    public ResponseEntity<List<EmployeeStatusCountResponseDTO>> getEmployeeCountByStatus() {
        return ResponseEntity.ok(employeeReportService.countEmployeesByStatus());
    }

    @Operation(
            summary = "Get employee count by type",
            description = "Returns the number of employees grouped by employee type."
    )
    @GetMapping("/reports/employees/by-type")
    public ResponseEntity<List<EmployeeTypeCountResponseDTO>> countEmployeesByType() {
        return ResponseEntity.ok(employeeReportService.countEmployeesByType());
    }

    @Operation(
            summary = "Get employee count by gender",
            description = "Returns the number of employees grouped by gender."
    )
    @GetMapping("/reports/employees/by-gender")
    public ResponseEntity<List<EmployeeGenderCountResponseDTO>> countEmployeesByGender() {
        return ResponseEntity.ok(employeeReportService.countEmployeesByGender());
    }
}