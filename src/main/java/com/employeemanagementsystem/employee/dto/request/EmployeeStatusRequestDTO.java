package com.employeemanagementsystem.employee.dto.request;

import com.employeemanagementsystem.employee.enums.EmployeeStatusEnums;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeStatusRequestDTO {

    @NotNull(message = "employeeStatus is required")
    private EmployeeStatusEnums employeeStatusEnums;
}
