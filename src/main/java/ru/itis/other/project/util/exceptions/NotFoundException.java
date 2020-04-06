package ru.itis.other.project.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Generates {@code 404 NOT FOUND} error response.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends ResponseException {

    public NotFoundException(String message, Object data) {
        super(HttpStatus.NOT_FOUND, message, data);
    }

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
