package ru.itis.other.project.services;

import ru.itis.other.project.dto.SignUpDto;
import ru.itis.other.project.util.exceptions.EmailAlreadyTakenException;

public interface SignUpService {

    void signUp(SignUpDto dto) throws EmailAlreadyTakenException;
}
