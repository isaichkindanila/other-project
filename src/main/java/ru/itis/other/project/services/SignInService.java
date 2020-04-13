package ru.itis.other.project.services;

import org.springframework.security.access.AccessDeniedException;
import ru.itis.other.project.dto.auth.SignInDto;
import ru.itis.other.project.models.User;

public interface SignInService {

    /**
     * @throws AccessDeniedException if user does not exist, not activated or provided password is incorrect
     */
    User signIn(SignInDto dto) throws AccessDeniedException;
}
