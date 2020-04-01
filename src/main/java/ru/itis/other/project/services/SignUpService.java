package ru.itis.other.project.services;

import ru.itis.other.project.dto.SignUpDto;
import ru.itis.other.project.dto.SignUpTokenDto;
import ru.itis.other.project.util.exceptions.EmailAlreadyTakenException;

public interface SignUpService {

    /**
     * @throws EmailAlreadyTakenException if email is already taken
     */
    SignUpTokenDto signUp(SignUpDto dto) throws EmailAlreadyTakenException;
}
