package ru.itis.other.project.util.exceptions;

public class EmailAlreadyTakenException extends ConflictException {

    public EmailAlreadyTakenException(String email) {
        super("email is already taken", email);
    }
}
