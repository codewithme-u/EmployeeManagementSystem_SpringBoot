package com.employeemanagementsystem.employee.enums;

public enum EmployeeStatusEnums {
    ACTIVE,
    INACTIVE,
    TERMINATED;

    public boolean canChangeTo(EmployeeStatusEnums newEmployeeStatusEnums) {

        return switch (this) {
            case ACTIVE -> newEmployeeStatusEnums == INACTIVE || newEmployeeStatusEnums == TERMINATED;
            case INACTIVE -> newEmployeeStatusEnums == ACTIVE || newEmployeeStatusEnums == TERMINATED;
            case TERMINATED -> false;

        };
    }

}


