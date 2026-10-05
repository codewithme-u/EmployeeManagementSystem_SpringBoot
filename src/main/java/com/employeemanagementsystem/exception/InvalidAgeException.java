package com.employeemanagementsystem.exception;

public class InvalidAgeException extends BusinessException {

    public InvalidAgeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidAgeException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}