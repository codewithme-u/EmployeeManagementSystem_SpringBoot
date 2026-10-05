package com.employeemanagementsystem.employee.service;

import com.employeemanagementsystem.employee.dto.request.EmployeeRequestDTO;
import com.employeemanagementsystem.employee.dto.request.EmployeeStatusRequestDTO;
import com.employeemanagementsystem.employee.dto.response.EmployeeResponseDTO;
import com.employeemanagementsystem.employee.dto.response.PageResponseDTO;
import com.employeemanagementsystem.employee.enums.EmployeeGenderEnums;
import com.employeemanagementsystem.employee.enums.EmployeeStatusEnums;

import java.util.List;

public interface EmployeeService {

    EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO);

    EmployeeResponseDTO getEmployeeById(Long id);

    List<EmployeeResponseDTO> createEmployees(List<EmployeeRequestDTO> employeeRequestDTO);

//    List<EmployeeResponseDTO> getAllEmployees();

    EmployeeResponseDTO updateEmployeeById(Long id, EmployeeRequestDTO employeeRequestDTO);

    void deleteEmployeeById(Long id);

    EmployeeResponseDTO changeEmployeeStatus(Long id, EmployeeStatusRequestDTO employeeStatusRequestDTO);

    //    List<EmployeeResponseDTO> getAllEmployees(String keyword);
//    List<EmployeeResponseDTO> getAllEmployees(String keyword, EmployeeStatus employeeStatus);
//    List<EmployeeResponseDTO> getAllEmployees(String keyword, EmployeeStatus employeeStatus, Long departmentId);
//    List<EmployeeResponseDTO> getAllEmployees(String keyword, EmployeeStatusEnums employeeStatusEnums, Long departmentId, EmployeeGenderEnums employeeGenderEnums);
    // PageResponseDTO<EmployeeResponseDTO> getAllEmployees(String keyword, EmployeeStatusEnums employeeStatusEnums, Long departmentId, EmployeeGenderEnums employeeGenderEnums, int page, int size);
    PageResponseDTO<EmployeeResponseDTO> getAllEmployees(String keyword, EmployeeStatusEnums employeeStatusEnums, Long departmentId, EmployeeGenderEnums employeeGenderEnums, int page, int size, String sort);


}
