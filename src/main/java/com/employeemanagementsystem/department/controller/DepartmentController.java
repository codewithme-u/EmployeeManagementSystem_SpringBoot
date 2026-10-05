package com.employeemanagementsystem.department.controller;

import com.employeemanagementsystem.annotation.EmployeeManagementRestController;
import com.employeemanagementsystem.department.dto.request.DepartmentRequestDTO;
import com.employeemanagementsystem.department.dto.response.DepartmentResponseDTO;
import com.employeemanagementsystem.department.service.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(
        name = "Departments",
        description = "Department management APIs"
)
@EmployeeManagementRestController
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/departments")
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@Valid @RequestBody DepartmentRequestDTO departmentRequestDTO) {
        DepartmentResponseDTO departmentResponseDTO = departmentService.createDepartment(departmentRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentResponseDTO);
    }

    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentByID(@PathVariable Long id) {
        DepartmentResponseDTO departmentResponseDTO = departmentService.getDepartmentByID(id);
        return ResponseEntity.ok().body(departmentResponseDTO);

    }

    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartment() {
        List<DepartmentResponseDTO> departmentResponseDTO = departmentService.getAllDepartment();
        return ResponseEntity.ok().body(departmentResponseDTO);

    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartmentById(@Valid @RequestBody DepartmentRequestDTO departmentRequestDTO, @PathVariable Long id) {
        DepartmentResponseDTO departmentResponseDTO = departmentService.updateDepartmentById(departmentRequestDTO, id);
        return ResponseEntity.ok(departmentResponseDTO);

    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Void> deleteDepartmentById(@PathVariable Long id) {
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.noContent().build();
    }


}
