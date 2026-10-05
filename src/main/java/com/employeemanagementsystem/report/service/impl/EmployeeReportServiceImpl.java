package com.employeemanagementsystem.report.service.impl;

import com.employeemanagementsystem.employee.enums.EmployeeStatusEnums;
import com.employeemanagementsystem.report.dto.response.EmployeeGenderCountResponseDTO;
import com.employeemanagementsystem.report.dto.response.EmployeeStatusCountResponseDTO;
import com.employeemanagementsystem.report.dto.response.EmployeeTypeCountResponseDTO;
import com.employeemanagementsystem.report.repository.DepartmentReportRepository;
import com.employeemanagementsystem.report.repository.EmployeeReportRepository;
import com.employeemanagementsystem.report.service.EmployeeReportService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeReportServiceImpl implements EmployeeReportService {

    private final EmployeeReportRepository employeeReportRepository;
    private final DepartmentReportRepository departmentReportRepository;

    public EmployeeReportServiceImpl(EmployeeReportRepository employeeReportRepository, DepartmentReportRepository departmentReportRepository) {
        this.employeeReportRepository = employeeReportRepository;
        this.departmentReportRepository = departmentReportRepository;
    }

    @Override
    public List<EmployeeStatusCountResponseDTO> countEmployeesByStatus() {
        Map<EmployeeStatusEnums, Long> countByStatus = new EnumMap<>(EmployeeStatusEnums.class);
        for (EmployeeStatusCountResponseDTO row : employeeReportRepository.countEmployeesByStatus()) {
            countByStatus.put(row.employeeStatus(), row.count());
        }
        List<EmployeeStatusCountResponseDTO> result = new ArrayList<>();
        for (EmployeeStatusEnums status : EmployeeStatusEnums.values()) {
            result.add(new EmployeeStatusCountResponseDTO(status, countByStatus.getOrDefault(status, 0L)));
        }
        return result;
    }


    @Override
    public List<EmployeeTypeCountResponseDTO> countEmployeesByType() {
        return employeeReportRepository.countEmployeesByType();
    }

    @Override
    public List<EmployeeGenderCountResponseDTO> countEmployeesByGender() {
        return employeeReportRepository.countEmployeesByGender();
    }


}
