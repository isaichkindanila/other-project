package ru.itis.other.project.util.exceptions;

import java.util.Map;

public class TokenNotFoundException extends NotFoundException {

    public TokenNotFoundException(String token) {
        super("token not found", Map.of("token", token));
    }
}
