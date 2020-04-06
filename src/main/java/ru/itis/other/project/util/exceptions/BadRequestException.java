package ru.itis.other.project.util.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Generates {@code 400 BAD REQUEST} error response.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends ResponseException {

    public BadRequestException(String message, Object data) {
        super(HttpStatus.BAD_REQUEST, message, data);
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
