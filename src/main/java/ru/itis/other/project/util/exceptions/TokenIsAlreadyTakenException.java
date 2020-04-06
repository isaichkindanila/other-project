package ru.itis.other.project.util.exceptions;

import java.util.Map;

public class TokenIsAlreadyTakenException extends ConflictException {

    public TokenIsAlreadyTakenException(String token) {
        super("token is already taken", Map.of("token", token));
    }
}
