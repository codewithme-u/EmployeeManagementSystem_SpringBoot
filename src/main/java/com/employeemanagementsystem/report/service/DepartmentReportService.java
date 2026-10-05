package com.employeemanagementsystem.report.service;

import com.employeemanagementsystem.report.dto.response.DepartmentAverageSalaryResponseDTO;
import com.employeemanagementsystem.report.dto.response.DepartmentCountResponseDTO;
import com.employeemanagementsystem.report.dto.response.DepartmentTotalSalaryResponseDTO;

import java.util.List;

public interface DepartmentReportService {
    List<DepartmentCountResponseDTO> countActiveEmployeesByDepartment();

    List<DepartmentAverageSalaryResponseDTO> findAverageSalaryByDepartment();

    List<DepartmentTotalSalaryResponseDTO> findTotalSalaryByDepartment();
}
