package ru.itis.other.project.util;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiErrorResponse {
    private final int status;
    private final String reason;

    public ApiErrorResponse(HttpStatus status, String reason) {
        this.status = status.value();
        this.reason = reason;
    }

    public ApiErrorResponse(HttpStatus status, Exception exception) {
        this(status, exception.getMessage());
    }
}
