package ru.itis.other.project.services;

import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.other.project.dto.JwtDto;
import ru.itis.other.project.models.User;

public interface JwtService {

    JwtDto generateJWT(User user);

    UserDetails parseToken(String token);
}
