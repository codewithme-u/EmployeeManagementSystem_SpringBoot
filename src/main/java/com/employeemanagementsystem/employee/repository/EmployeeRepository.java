package com.employeemanagementsystem.employee.repository;

import com.employeemanagementsystem.employee.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>, JpaSpecificationExecutor<EmployeeEntity> {
    boolean existsByEmployeeEmail(String employeeEmail);

    boolean existsByEmployeeEmailAndEmployeeIdNot(String employeeEmail, Long employeeId);


}
