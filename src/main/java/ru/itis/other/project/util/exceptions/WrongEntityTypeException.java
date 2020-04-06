package ru.itis.other.project.util.exceptions;

import java.util.Map;

public class WrongEntityTypeException extends BadRequestException {

    public static WrongEntityTypeException expectedFile() {
        return new WrongEntityTypeException("file", "directory");
    }

    public static WrongEntityTypeException expectedDirectory() {
        return new WrongEntityTypeException("directory", "file");
    }

    private WrongEntityTypeException(String expected, String actual) {
        super("wrong entity type", Map.of("expected", expected, "actual", actual));
    }
}
