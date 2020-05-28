package ru.itis.other.project.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itis.other.project.util.ApiErrorResponse;
import ru.itis.other.project.util.exceptions.ResponseException;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice("ru.itis.other.project.controllers.api")
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<?> errorResponse(HttpStatus status, Exception e) {
        return new ApiErrorResponse(status, e.getMessage()).toResponseEntity();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        @SuppressWarnings("ConstantConditions")
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return new ApiErrorResponse(BAD_REQUEST, "invalid parameters", errors)
                .toResponseEntity();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException e) {
        return errorResponse(FORBIDDEN, e);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException e) {
        return errorResponse(UNAUTHORIZED, e);
    }

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<?> handleResponseException(ResponseException e) {
        return e.toApiErrorResponse().toResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericError(Exception e) {
        return errorResponse(INTERNAL_SERVER_ERROR, e);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorResponse response = new ApiErrorResponse(status, ex.getMessage(), body);
        return new ResponseEntity<>(response, headers, status);
    }
}
