package ru.itis.other.project.util.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.itis.other.project.util.ApiErrorResponse;

import java.util.Map;

@Getter
public abstract class ResponseException extends RuntimeException {

    private final HttpStatus status;
    private final Object data;

    public ResponseException(HttpStatus status, String message, Object data) {
        super(message);
        this.status = status;
        this.data = data;
    }

    public ResponseException(HttpStatus status, String message) {
        this(status, message, Map.of());
    }

    public ApiErrorResponse toApiErrorResponse() {
        return new ApiErrorResponse(getStatus(), getMessage(), getData());
    }
}
