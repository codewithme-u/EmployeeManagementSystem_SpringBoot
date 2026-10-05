package com.employeemanagementsystem.report.repository;

import com.employeemanagementsystem.department.entity.DepartmentEntity;
import com.employeemanagementsystem.employee.enums.EmployeeStatusEnums;
import com.employeemanagementsystem.report.dto.response.DepartmentAverageSalaryResponseDTO;
import com.employeemanagementsystem.report.dto.response.DepartmentCountResponseDTO;
import com.employeemanagementsystem.report.dto.response.DepartmentTotalSalaryResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentReportRepository extends JpaRepository<DepartmentEntity, Long> {

    @Query("""
            SELECT new com.employeemanagementsystem.report.dto.response.DepartmentCountResponseDTO(
                d.departmentId, d.departmentName, COUNT(e))
            FROM DepartmentEntity d
            LEFT JOIN EmployeeEntity e
                ON e.departmentEntity = d
                AND e.employeeStatusEnums = :employeeStatusEnums
            GROUP BY d.departmentId, d.departmentName
            ORDER BY d.departmentName
            """)
    List<DepartmentCountResponseDTO> countEmployeesByDepartment(EmployeeStatusEnums employeeStatusEnums);

    @Query("""
            SELECT new com.employeemanagementsystem.report.dto.response.DepartmentAverageSalaryResponseDTO(
                d.departmentId,
                d.departmentName,
                AVG(e.baseSalary))
            FROM DepartmentEntity d
            JOIN EmployeeEntity e
                ON e.departmentEntity = d
                AND e.employeeStatusEnums =
                    com.employeemanagementsystem.employee.enums.EmployeeStatusEnums.ACTIVE
            GROUP BY d.departmentId, d.departmentName
            ORDER BY d.departmentName
            """)
    List<DepartmentAverageSalaryResponseDTO> findAverageSalaryByDepartment();

    @Query("""
            SELECT new com.employeemanagementsystem.report.dto.response.DepartmentTotalSalaryResponseDTO(
                d.departmentId,
                d.departmentName,
                SUM(e.baseSalary))
            FROM DepartmentEntity d
            JOIN EmployeeEntity e
                ON e.departmentEntity = d
                AND e.employeeStatusEnums = com.employeemanagementsystem.employee.enums.EmployeeStatusEnums.ACTIVE
            GROUP BY d.departmentId, d.departmentName
            ORDER BY d.departmentName
            """)
    List<DepartmentTotalSalaryResponseDTO> findTotalSalaryByDepartment();
}