package com.employeemanagementsystem.department.service;

import com.employeemanagementsystem.department.dto.request.DepartmentRequestDTO;
import com.employeemanagementsystem.department.dto.response.DepartmentResponseDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentResponseDTO createDepartment(DepartmentRequestDTO departmentRequestDTO);

    DepartmentResponseDTO getDepartmentByID(Long id);

    List<DepartmentResponseDTO> getAllDepartment();

    DepartmentResponseDTO updateDepartmentById(DepartmentRequestDTO departmentRequestDTO, Long id);

    void deleteDepartmentById(Long id);
}
