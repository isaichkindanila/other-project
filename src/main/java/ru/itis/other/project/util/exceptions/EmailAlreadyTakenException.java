package ru.itis.other.project.util.exceptions;

import java.util.Map;

public class EmailAlreadyTakenException extends ConflictException {

    public EmailAlreadyTakenException(String email) {
        super("email is already taken", Map.of("email", email));
    }
}
