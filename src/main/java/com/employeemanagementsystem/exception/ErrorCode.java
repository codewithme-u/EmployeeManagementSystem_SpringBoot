package com.employeemanagementsystem.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // Department
    DEPARTMENT_NOT_FOUND("DEPT001", HttpStatus.NOT_FOUND, "Department not found"),
    DEPARTMENT_NAME_ALREADY_EXISTS("DEPT002", HttpStatus.CONFLICT, "Department name already exists"),
    DEPARTMENT_DELETE_NOT_ALLOWED("DEPT003", HttpStatus.CONFLICT, "Department cannot be deleted because employees exist"),

    // Employee
    EMPLOYEE_NOT_FOUND("EMP001", HttpStatus.NOT_FOUND, "Employee not found"),
    EMPLOYEE_EMAIL_ALREADY_EXISTS("EMP002", HttpStatus.CONFLICT, "Employee email already exists"),
    INVALID_EMPLOYEE_STATUS_TRANSITION("EMP003", HttpStatus.CONFLICT, "Invalid employee status transition"),
    EMPLOYEE_BELOW_MINIMUM_AGE("EMP004", HttpStatus.BAD_REQUEST, "Employee is below the minimum age"),

    // Request
    VALIDATION_FAILED("REQ001", HttpStatus.BAD_REQUEST, "Validation failed"),
    MALFORMED_JSON("REQ002", HttpStatus.BAD_REQUEST, "Malformed JSON or invalid value in request body"),
    INVALID_PARAMETER_TYPE("REQ003", HttpStatus.BAD_REQUEST, "Invalid parameter type"),
    INVALID_PAGINATION_PARAMETER("REQ004", HttpStatus.BAD_REQUEST, "Invalid pagination parameter"),
    INVALID_SORTING_PARAMETER("REQ005", HttpStatus.BAD_REQUEST, "Invalid sorting parameter"),
    HTTP_METHOD_NOT_ALLOWED("REQ006", HttpStatus.METHOD_NOT_ALLOWED, "HTTP method not allowed"),
    UNSUPPORTED_MEDIA_TYPE("REQ007", HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported media type"),
    RESOURCE_NOT_FOUND("REQ008", HttpStatus.NOT_FOUND, "Resource not found"),

    // System
    DATABASE_INTEGRITY_VIOLATION("SYS001", HttpStatus.CONFLICT, "The request violates a data constraint"),
    INTERNAL_SERVER_ERROR("SYS002", HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"),

    // Security (Phase 7)
    AUTHENTICATION_FAILED("SEC001", HttpStatus.UNAUTHORIZED, "Authentication required or failed"),
    ACCESS_DENIED("SEC002", HttpStatus.FORBIDDEN, "Access denied");

    private final String code;
    private final HttpStatus status;
    private final String defaultMessage;

    ErrorCode(String code, HttpStatus status, String defaultMessage) {
        this.code = code;
        this.status = status;
        this.defaultMessage = defaultMessage;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}