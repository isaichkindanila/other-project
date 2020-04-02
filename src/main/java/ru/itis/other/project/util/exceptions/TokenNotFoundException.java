package ru.itis.other.project.util.exceptions;

public class TokenNotFoundException extends NotFoundException {

    public TokenNotFoundException(String token) {
        super("token not found (" + token + ")");
    }
}
