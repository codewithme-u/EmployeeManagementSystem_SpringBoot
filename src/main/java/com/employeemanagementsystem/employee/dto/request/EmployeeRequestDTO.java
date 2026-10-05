package com.employeemanagementsystem.employee.dto.request;

import com.employeemanagementsystem.employee.enums.EmployeeGenderEnums;
import com.employeemanagementsystem.employee.enums.EmployeeTypeEnums;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    private String employeeName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String employeeEmail;

    @NotBlank(message = "Phone is required")
    @Size(max = 14, message = "Phone must not exceed 14 characters")
    @Pattern(
            regexp = "^(\\+977)?9[678]\\d{8}$",
            message = "Phone number must be a valid Nepal mobile number"
    )
    private String employeePhone;

    @NotBlank(message = "Address is required")
    @Size(max = 50, message = "Address must not exceed 50 characters")
    private String employeeAddress;

    @NotNull(message = "Gender is required")
    private EmployeeGenderEnums employeeGenderEnums;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Hire date is required")
    @PastOrPresent(message = "Hire date cannot be in the future")
    private LocalDate hireDate;

    @NotNull(message = "Employee type is required")
    private EmployeeTypeEnums employeeTypeEnums;

    @NotNull(message = "Department id is required")
    @Positive(message = "Department id must be positive")
    private Long departmentId;

    @NotNull(message = "Base salary is required")
    @PositiveOrZero(message = "Salary cannot be negative")
    @Digits(integer = 10, fraction = 2, message = "Salary must have at most 10 digits and 2 decimals")
    private Double baseSalary;
}
