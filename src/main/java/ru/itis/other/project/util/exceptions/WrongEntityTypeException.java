package ru.itis.other.project.util.exceptions;

import java.util.Map;

public class WrongEntityTypeException extends BadRequestException {

    public WrongEntityTypeException(String expected, String actual) {
        super("wrong entity type", Map.of("expected", expected, "actual", actual));
    }
}
