package ru.itis.other.project.services;

import ru.itis.other.project.dto.SignUpDto;
import ru.itis.other.project.util.exceptions.EmailAlreadyTakenException;

public interface SignUpService {

    /**
     * @throws EmailAlreadyTakenException if email is already taken
     */
    void signUp(SignUpDto dto) throws EmailAlreadyTakenException;
}
