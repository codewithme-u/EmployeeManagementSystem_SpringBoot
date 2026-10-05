package com.employeemanagementsystem.exception;

public class InvalidParameterException extends BusinessException {

    public InvalidParameterException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidParameterException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}