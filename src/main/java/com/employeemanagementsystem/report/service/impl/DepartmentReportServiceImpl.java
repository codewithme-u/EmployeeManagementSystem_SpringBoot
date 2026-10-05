package com.employeemanagementsystem.report.service.impl;

import com.employeemanagementsystem.employee.enums.EmployeeStatusEnums;
import com.employeemanagementsystem.report.dto.response.DepartmentAverageSalaryResponseDTO;
import com.employeemanagementsystem.report.dto.response.DepartmentCountResponseDTO;
import com.employeemanagementsystem.report.dto.response.DepartmentTotalSalaryResponseDTO;
import com.employeemanagementsystem.report.repository.DepartmentReportRepository;
import com.employeemanagementsystem.report.service.DepartmentReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentReportServiceImpl implements DepartmentReportService {
    private final DepartmentReportRepository departmentReportRepository;

    public DepartmentReportServiceImpl(DepartmentReportRepository departmentReportRepository) {
        this.departmentReportRepository = departmentReportRepository;

    }

    @Override
    public List<DepartmentCountResponseDTO> countActiveEmployeesByDepartment() {
        return departmentReportRepository.countEmployeesByDepartment(EmployeeStatusEnums.ACTIVE);
    }

    @Override
    public List<DepartmentAverageSalaryResponseDTO> findAverageSalaryByDepartment() {
        return departmentReportRepository.findAverageSalaryByDepartment();
    }

    @Override
    public List<DepartmentTotalSalaryResponseDTO> findTotalSalaryByDepartment() {
        return departmentReportRepository.findTotalSalaryByDepartment();
    }
}
