package ru.itis.other.project.util.exceptions;

import java.util.Map;

public class EntityNotFoundException extends NotFoundException {

    public EntityNotFoundException(String token) {
        super("entity not found", Map.of("token", token));
    }

    public EntityNotFoundException(Object data) {
        super("entity not found", data);
    }
}
