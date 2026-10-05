package com.employeemanagementsystem.department.mapper;


import com.employeemanagementsystem.department.dto.request.DepartmentRequestDTO;
import com.employeemanagementsystem.department.dto.response.DepartmentResponseDTO;
import com.employeemanagementsystem.department.entity.DepartmentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentEntity toEntity(DepartmentRequestDTO departmentRequestDTO);

    DepartmentResponseDTO toResponse(DepartmentEntity departmentEntity);

    List<DepartmentResponseDTO> toResponseList(List<DepartmentEntity> departmentEntity);


}
