package com.employeemanagementsystem.employee.controller;

import com.employeemanagementsystem.annotation.EmployeeManagementRestController;
import com.employeemanagementsystem.employee.dto.request.EmployeeRequestDTO;
import com.employeemanagementsystem.employee.dto.request.EmployeeStatusRequestDTO;
import com.employeemanagementsystem.employee.dto.response.EmployeeResponseDTO;
import com.employeemanagementsystem.employee.dto.response.PageResponseDTO;
import com.employeemanagementsystem.employee.enums.EmployeeGenderEnums;
import com.employeemanagementsystem.employee.enums.EmployeeStatusEnums;
import com.employeemanagementsystem.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Employees",
        description = "Employee management APIs"
)

@EmployeeManagementRestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        EmployeeResponseDTO employeeResponseDTO = employeeService.createEmployee(employeeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponseDTO);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDTO employeeResponseDTO = employeeService.getEmployeeById(id);
        return ResponseEntity.ok().body(employeeResponseDTO);
    }

    @PostMapping("/employees/bulk")
    public ResponseEntity<List<EmployeeResponseDTO>> createEmployees(@RequestBody List<@Valid EmployeeRequestDTO> employeeRequestDTO) {
        List<EmployeeResponseDTO> employeeResponseDTO = employeeService.createEmployees(employeeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponseDTO);
    }

//    @GetMapping()
//    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
//        List<EmployeeResponseDTO> employeeResponseDTOS = employeeService.getAllEmployees();
//        return ResponseEntity.ok().body(employeeResponseDTOS);
//    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployeeById(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO, @PathVariable Long id) {
        EmployeeResponseDTO employeeResponseDTO = employeeService.updateEmployeeById(id, employeeRequestDTO);
        return ResponseEntity.ok().body(employeeResponseDTO);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/employees/{id}/status")
    public ResponseEntity<EmployeeResponseDTO> changeEmployeeStatus(@PathVariable Long id, @Valid @RequestBody EmployeeStatusRequestDTO employeeStatusRequestDTO) {
        EmployeeResponseDTO employeeResponseDTO = employeeService.changeEmployeeStatus(id, employeeStatusRequestDTO);
        return ResponseEntity.ok(employeeResponseDTO);
    }

//    @GetMapping()
//    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(@RequestParam(required = false) String keyword) {
//        List<EmployeeResponseDTO> employeeResponseDTOS = employeeService.getAllEmployees(keyword);
//        return ResponseEntity.ok().body(employeeResponseDTOS);
//    }

//    @GetMapping()
//    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(@RequestParam(required = false) String keyword, @RequestParam(required = false) EmployeeStatus employeeStatus) {
//        List<EmployeeResponseDTO> employeeResponseDTOS = employeeService.getAllEmployees(keyword, employeeStatus);
//        return ResponseEntity.ok().body(employeeResponseDTOS);
//
//    }

//    @GetMapping()
//    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(@RequestParam(required = false) String keyword, @RequestParam(required = false) EmployeeStatus employeeStatus, @RequestParam(required = false) Long departmentId) {
//
//        List<EmployeeResponseDTO> employeeResponseDTOS = employeeService.getAllEmployees(keyword, employeeStatus, departmentId);
//        return ResponseEntity.ok().body(employeeResponseDTOS);
//    }


//    @GetMapping()
//    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(@RequestParam(required = false) String keyword, @RequestParam(required = false) EmployeeStatusEnums employeeStatusEnums, @RequestParam(required = false) Long departmentId, EmployeeGenderEnums employeeGenderEnums) {
//        List<EmployeeResponseDTO> employeeResponseDTOS = employeeService.getAllEmployees(keyword, employeeStatusEnums, departmentId, employeeGenderEnums);
//        return ResponseEntity.ok().body(employeeResponseDTOS);
//    }

//    @GetMapping()
//    public ResponseEntity<PageResponseDTO<EmployeeResponseDTO>> getAllEmployees(
//            @RequestParam(required = false) String keyword,
//            @RequestParam(required = false) EmployeeStatusEnums employeeStatusEnums,
//            @RequestParam(required = false) Long departmentId,
//            @RequestParam(required = false) EmployeeGenderEnums employeeGenderEnums,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
//        PageResponseDTO<EmployeeResponseDTO> employeeResponseDTOPageResponseDTO = employeeService.getAllEmployees(
//                keyword, employeeStatusEnums, departmentId, employeeGenderEnums, page, size);
//        return ResponseEntity.ok().body(employeeResponseDTOPageResponseDTO);
//    }

    @GetMapping("/employees")
    public ResponseEntity<PageResponseDTO<EmployeeResponseDTO>> getAllEmployees(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) EmployeeStatusEnums employeeStatusEnums,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) EmployeeGenderEnums employeeGenderEnums,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {
        PageResponseDTO<EmployeeResponseDTO> employeeResponseDTOPageResponseDTO = employeeService.getAllEmployees(
                keyword, employeeStatusEnums, departmentId, employeeGenderEnums, page, size, sort
        );
        return ResponseEntity.ok().body(employeeResponseDTOPageResponseDTO);
    }

}
