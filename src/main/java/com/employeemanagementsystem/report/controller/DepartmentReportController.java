package com.employeemanagementsystem.report.controller;

import com.employeemanagementsystem.annotation.EmployeeManagementRestController;
import com.employeemanagementsystem.report.dto.response.DepartmentAverageSalaryResponseDTO;
import com.employeemanagementsystem.report.dto.response.DepartmentCountResponseDTO;
import com.employeemanagementsystem.report.dto.response.DepartmentTotalSalaryResponseDTO;
import com.employeemanagementsystem.report.service.DepartmentReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(
        name = "Department Reports",
        description = "Department reporting APIs"
)

@EmployeeManagementRestController
public class DepartmentReportController {

    private final DepartmentReportService departmentReportService;

    public DepartmentReportController(DepartmentReportService departmentReportService) {
        this.departmentReportService = departmentReportService;
    }


    @Operation(
            summary = "Get active employee count by department",
            description = "Returns the number of active employees grouped by department."
    )
    @GetMapping("/reports/departments/employee-count")
    public ResponseEntity<List<DepartmentCountResponseDTO>> getActiveEmployeeCountByDepartment() {
        return ResponseEntity.ok(departmentReportService.countActiveEmployeesByDepartment());
    }


    @Operation(
            summary = "Get average salary by department",
            description = "Returns the average salary of active employees grouped by department."
    )
    @GetMapping("/reports/departments/average-salary")
    public ResponseEntity<List<DepartmentAverageSalaryResponseDTO>> findAverageSalaryByDepartment() {
        return ResponseEntity.ok(departmentReportService.findAverageSalaryByDepartment());
    }

    @Operation(
            summary = "Get total salary by department",
            description = "Returns the total salary of active employees grouped by department."
    )
    @GetMapping("/reports/departments/total-salary")
    public ResponseEntity<List<DepartmentTotalSalaryResponseDTO>> findTotalSalaryByDepartment() {
        return ResponseEntity.ok(departmentReportService.findTotalSalaryByDepartment());
    }
}
