package ru.itis.other.project.services;

import ru.itis.other.project.dto.SignUpTokenDto;
import ru.itis.other.project.models.User;
import ru.itis.other.project.util.exceptions.TokenNotFoundException;

public interface SignUpTokenService {

    SignUpTokenDto createTokenFor(User user);

    /**
     * @return true if token was confirmed, false if token was already confirmed
     * @throws TokenNotFoundException if token is not present in database
     */
    boolean confirm(String token);
}
