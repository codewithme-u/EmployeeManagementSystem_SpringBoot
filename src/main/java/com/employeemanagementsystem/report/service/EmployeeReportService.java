package com.employeemanagementsystem.report.service;

import com.employeemanagementsystem.report.dto.response.EmployeeGenderCountResponseDTO;
import com.employeemanagementsystem.report.dto.response.EmployeeStatusCountResponseDTO;
import com.employeemanagementsystem.report.dto.response.EmployeeTypeCountResponseDTO;

import java.util.List;

public interface EmployeeReportService {
    List<EmployeeStatusCountResponseDTO> countEmployeesByStatus();

    List<EmployeeTypeCountResponseDTO> countEmployeesByType();

    List<EmployeeGenderCountResponseDTO> countEmployeesByGender();


}
