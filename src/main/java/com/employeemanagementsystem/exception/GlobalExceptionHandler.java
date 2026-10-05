package com.employeemanagementsystem.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        ErrorCode errorCode = ex.getErrorCode();
        log.warn("{} {} -> {}", errorCode.getCode(), request.getRequestURI(), ex.getMessage());
        return build(errorCode, ex.getMessage(), request.getRequestURI(), List.of(), new HttpHeaders());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.warn("Data integrity violation at {}: {}", request.getRequestURI(), ex.getMostSpecificCause().getMessage());
        return build(ErrorCode.DATABASE_INTEGRITY_VIOLATION,
                ErrorCode.DATABASE_INTEGRITY_VIOLATION.getDefaultMessage(),
                request.getRequestURI(), List.of(), new HttpHeaders());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpected(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error at {}", request.getRequestURI(), ex);
        return build(ErrorCode.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SERVER_ERROR.getDefaultMessage(),
                request.getRequestURI(), List.of(), new HttpHeaders());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldErrorDetail> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new FieldErrorDetail(fe.getField(), fe.getDefaultMessage()))
                .toList();
        log.warn("Validation failed at {}: {}", pathOf(request), errors);
        return build(ErrorCode.VALIDATION_FAILED, ErrorCode.VALIDATION_FAILED.getDefaultMessage(),
                pathOf(request), errors, headers);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.warn("Unreadable request body at {}: {}", pathOf(request), ex.getMostSpecificCause().getMessage());
        return build(ErrorCode.MALFORMED_JSON, ErrorCode.MALFORMED_JSON.getDefaultMessage(),
                pathOf(request), List.of(), headers);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String name = (ex instanceof MethodArgumentTypeMismatchException m) ? m.getName() : ex.getPropertyName();
        String message = "Invalid value '" + ex.getValue() + "' for parameter '" + name + "'";
        log.warn("Type mismatch at {}: {}", pathOf(request), message);
        return build(ErrorCode.INVALID_PARAMETER_TYPE, message, pathOf(request), List.of(), headers);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        ErrorCode errorCode = switch (statusCode.value()) {
            case 404 -> ErrorCode.RESOURCE_NOT_FOUND;
            case 405 -> ErrorCode.HTTP_METHOD_NOT_ALLOWED;
            case 415 -> ErrorCode.UNSUPPORTED_MEDIA_TYPE;
            case 400 -> ErrorCode.VALIDATION_FAILED;
            default -> ErrorCode.INTERNAL_SERVER_ERROR;
        };
        if (statusCode.value() >= 500) {
            log.error(
                    "{} at {}: {}",
                    statusCode.value(),
                    pathOf(request),
                    ex.getMessage(),
                    ex
            );
        } else {
            log.warn(
                    "{} at {}: {}",
                    statusCode.value(),
                    pathOf(request),
                    ex.getMessage()
            );
        }
        return build(errorCode, errorCode.getDefaultMessage(), pathOf(request), List.of(), headers);
    }

    private ResponseEntity<Object> build(ErrorCode errorCode, String message, String path,
                                         List<FieldErrorDetail> errors, HttpHeaders headers) {
        ErrorResponse body = new ErrorResponse(
                Instant.now(),
                errorCode.getStatus().value(),
                errorCode.getCode(),
                errorCode.getStatus().getReasonPhrase(),
                message,
                path,
                errors);
        return ResponseEntity.status(errorCode.getStatus()).headers(headers).body(body);
    }

    private String pathOf(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}