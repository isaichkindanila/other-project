package ru.itis.other.project.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itis.other.project.util.ApiErrorResponse;
import ru.itis.other.project.util.exceptions.ConflictException;
import ru.itis.other.project.util.exceptions.NotFoundException;

@ControllerAdvice("ru.itis.other.project.controllers.api")
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException e) {
        return new ApiErrorResponse(HttpStatus.FORBIDDEN, e).toResponseEntity();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException e) {
        return new ApiErrorResponse(HttpStatus.FORBIDDEN, e).toResponseEntity();
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflict(ConflictException e) {
        return new ApiErrorResponse(HttpStatus.CONFLICT, e).toResponseEntity();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException e) {
        return new ApiErrorResponse(HttpStatus.NOT_FOUND, e).toResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericError(Exception e) {
        return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e).toResponseEntity();
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorResponse(status, ex.getMessage()), headers, status);
    }
}
