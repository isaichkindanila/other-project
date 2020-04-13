package ru.itis.other.project.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.other.project.dto.auth.JwtDto;
import ru.itis.other.project.models.User;

public interface JwtService {

    JwtDto generateJWT(User user);

    UserDetails parseToken(String token);
}
