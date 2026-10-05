package com.employeemanagementsystem.employee.mapper;


import com.employeemanagementsystem.employee.dto.request.EmployeeRequestDTO;
import com.employeemanagementsystem.employee.dto.response.EmployeeResponseDTO;
import com.employeemanagementsystem.employee.entity.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeEntity toEntity(EmployeeRequestDTO employeeRequestDTO);

    @Mapping(
            source = "departmentEntity.departmentId",
            target = "departmentId"
    )
    @Mapping(
            source = "departmentEntity.departmentName",
            target = "departmentName"
    )
    EmployeeResponseDTO toResponse(EmployeeEntity employeeEntity);

    List<EmployeeResponseDTO> toResponseList(List<EmployeeEntity> employeeEntities);
}
