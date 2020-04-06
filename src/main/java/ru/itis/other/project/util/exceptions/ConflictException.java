package ru.itis.other.project.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Generates {@code 409 CONFLICT} error response.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends ResponseException {

    public ConflictException(String message, Object data) {
        super(HttpStatus.CONFLICT, message, data);
    }

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
