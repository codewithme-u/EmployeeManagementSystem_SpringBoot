package com.employeemanagementsystem.employee.specification;

import com.employeemanagementsystem.employee.entity.EmployeeEntity;
import com.employeemanagementsystem.employee.enums.EmployeeGenderEnums;
import com.employeemanagementsystem.employee.enums.EmployeeStatusEnums;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {


    private EmployeeSpecification() {
        /*
        No constructor written → Java adds a public one → objects can be created
        Private empty one      → Java adds nothing       → new EmployeeSpecification() is a compile error
        * */
    }


    //searching
    public static Specification<EmployeeEntity> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isBlank()) {
                return criteriaBuilder.conjunction();
            }
            String pattern = "%" + keyword.trim().toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("employeeName")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("employeeEmail")), pattern),
                    criteriaBuilder.like(root.get("employeePhone"), pattern)
            );
        };
    }

    //filtering
    public static Specification<EmployeeEntity> hasStatus(EmployeeStatusEnums employeeStatusEnums) {
        return (root, query, criteriaBuilder) -> {
            if (employeeStatusEnums == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(
                    root.get("employeeStatusEnums"), employeeStatusEnums
            );
        };
    }


    public static Specification<EmployeeEntity> hasDepartment(Long departmentId) {
        return (root, query, criteriaBuilder) -> {
            if (departmentId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("departmentEntity").get("departmentId"), departmentId);
        };
    }

    public static Specification<EmployeeEntity> hasGender(EmployeeGenderEnums employeeGenderEnums) {
        return (root, query, criteriaBuilder) -> {
            if (employeeGenderEnums == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("employeeGenderEnums"), employeeGenderEnums);

        };

    }
}
