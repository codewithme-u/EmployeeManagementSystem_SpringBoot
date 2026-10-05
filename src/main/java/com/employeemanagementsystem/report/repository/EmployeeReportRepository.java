package com.employeemanagementsystem.report.repository;

import com.employeemanagementsystem.employee.entity.EmployeeEntity;
import com.employeemanagementsystem.report.dto.response.EmployeeGenderCountResponseDTO;
import com.employeemanagementsystem.report.dto.response.EmployeeStatusCountResponseDTO;
import com.employeemanagementsystem.report.dto.response.EmployeeTypeCountResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeReportRepository extends JpaRepository<EmployeeEntity, Long> {

    @Query("""
            SELECT new com.employeemanagementsystem.report.dto.response.EmployeeStatusCountResponseDTO(
                e.employeeStatusEnums, COUNT(e))
            FROM EmployeeEntity e
            GROUP BY e.employeeStatusEnums
            """)
    List<EmployeeStatusCountResponseDTO> countEmployeesByStatus();

    @Query("""
            SELECT new com.employeemanagementsystem.report.dto.response.EmployeeTypeCountResponseDTO(
                e.employeeTypeEnums,
                COUNT(e))
            FROM EmployeeEntity e
            GROUP BY e.employeeTypeEnums
            """)
    List<EmployeeTypeCountResponseDTO> countEmployeesByType();

    @Query("""
            SELECT new com.employeemanagementsystem.report.dto.response.EmployeeGenderCountResponseDTO(
                e.employeeGenderEnums,
                COUNT(e))
            FROM EmployeeEntity e
            GROUP BY e.employeeGenderEnums
            """)
    List<EmployeeGenderCountResponseDTO> countEmployeesByGender();


}