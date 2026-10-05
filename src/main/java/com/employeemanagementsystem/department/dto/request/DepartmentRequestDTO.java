package com.employeemanagementsystem.department.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequestDTO {

    @NotBlank
    @Size(min = 3, max = 50)
    private String departmentName;

    @Size(max = 50)
    private String departmentDescription;

}
