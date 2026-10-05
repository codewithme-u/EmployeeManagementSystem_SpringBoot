package com.employeemanagementsystem.exception;

public class BusinessRuleException extends BusinessException {

    public BusinessRuleException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BusinessRuleException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}