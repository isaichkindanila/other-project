package ru.itis.other.project.util.exceptions;

public class InvalidKeyException extends BadRequestException {

    public InvalidKeyException() {
        super("invalid key");
    }
}
