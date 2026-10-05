package com.employeemanagementsystem.department.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentResponseDTO {

    private Long departmentId;
    private String departmentName;
    private String departmentDescription;
}