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

@ControllerAdvice("ru.itis.other.project.controllers.api")
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<?> errorResponse(HttpStatus status, Exception e) {
        return new ResponseEntity<>(new ApiErrorResponse(status, e), status);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException e) {
        return errorResponse(HttpStatus.FORBIDDEN, e);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handeBadCredentials(BadCredentialsException e) {
        return errorResponse(HttpStatus.FORBIDDEN, e);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflict(ConflictException e) {
        return errorResponse(HttpStatus.CONFLICT, e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericError(Exception e) {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ApiErrorResponse(status, ex.getMessage()), headers, status);
    }
}
