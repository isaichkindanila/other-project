package ru.itis.other.project.util;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Getter
@ToString
public class ApiErrorResponse {

    private final int status;
    private final String reason;
    private final Object data;

    public ApiErrorResponse(HttpStatus status, String reason, Object data) {
        this.status = status.value();
        this.reason = reason;
        this.data = data;
    }

    public ApiErrorResponse(HttpStatus status, String reason) {
        this(status, reason, Map.of());
    }

    public ResponseEntity<ApiErrorResponse> toResponseEntity() {
        return ResponseEntity.status(status).body(this);
    }
}
