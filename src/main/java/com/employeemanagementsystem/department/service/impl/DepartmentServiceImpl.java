package com.employeemanagementsystem.department.service.impl;

import com.employeemanagementsystem.department.dto.request.DepartmentRequestDTO;
import com.employeemanagementsystem.department.dto.response.DepartmentResponseDTO;
import com.employeemanagementsystem.department.entity.DepartmentEntity;
import com.employeemanagementsystem.department.mapper.DepartmentMapper;
import com.employeemanagementsystem.department.repository.DepartmentRepository;
import com.employeemanagementsystem.department.service.DepartmentService;
import com.employeemanagementsystem.exception.DuplicateResourceException;
import com.employeemanagementsystem.exception.ErrorCode;
import com.employeemanagementsystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO departmentRequestDTO) {
        if (departmentRepository.existsByDepartmentName(departmentRequestDTO.getDepartmentName())) {
            throw new DuplicateResourceException(
                    ErrorCode.DEPARTMENT_NAME_ALREADY_EXISTS,
                    "Department name already exists: " + departmentRequestDTO.getDepartmentName());
        }
        //DTO->Entity
        DepartmentEntity departmentEntity = departmentMapper.toEntity(departmentRequestDTO);
        //Entity->Database
        DepartmentEntity savedDepartmentEntity = departmentRepository.save(departmentEntity);
        //Entity->Response DTO
        return departmentMapper.toResponse(savedDepartmentEntity);
    }

    @Override
    public DepartmentResponseDTO getDepartmentByID(Long id) {
        //DataBase->Entity
        DepartmentEntity departmentEntity = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.DEPARTMENT_NOT_FOUND, "Department not found with id: " + id));
        //Entity->Response DTO
        return departmentMapper.toResponse(departmentEntity);
    }

    @Override
    public List<DepartmentResponseDTO> getAllDepartment() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        return departmentMapper.toResponseList(departmentEntities);
    }

    @Override
    public DepartmentResponseDTO updateDepartmentById(DepartmentRequestDTO departmentRequestDTO, Long id) {
        DepartmentEntity departmentEntity = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.DEPARTMENT_NOT_FOUND, "Department not found with id: " + id));
        departmentEntity.setDepartmentName(departmentRequestDTO.getDepartmentName());
        departmentEntity.setDepartmentDescription(departmentRequestDTO.getDepartmentDescription());

        DepartmentEntity savedDepartmentEntity = departmentRepository.save(departmentEntity);
        return departmentMapper.toResponse(savedDepartmentEntity);
    }

    @Override
    public void deleteDepartmentById(Long id) {
        DepartmentEntity departmentEntity = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorCode.DEPARTMENT_NOT_FOUND, "Department not found with id: " + id));
        departmentRepository.delete(departmentEntity);
    }
}
