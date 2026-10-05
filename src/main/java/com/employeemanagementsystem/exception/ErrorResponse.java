package com.employeemanagementsystem.exception;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        Instant timestamp,
        int status,
        String code,
        String error,
        String message,
        String path,
        List<FieldErrorDetail> errors
) {
}