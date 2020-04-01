package ru.itis.other.project.services;

import ru.itis.other.project.dto.SignUpDto;
import ru.itis.other.project.dto.SignUpTokenDto;
import ru.itis.other.project.util.exceptions.EmailAlreadyTakenException;
import ru.itis.other.project.util.exceptions.TokenNotFoundException;

public interface SignUpService {

    /**
     * @throws EmailAlreadyTakenException if email is already taken
     */
    SignUpTokenDto signUp(SignUpDto dto) throws EmailAlreadyTakenException;

    /**
     * @return true if token was confirmed, false if token was already confirmed
     * @throws TokenNotFoundException if token is not present in database
     */
    boolean confirm(String token) throws TokenNotFoundException;
}
