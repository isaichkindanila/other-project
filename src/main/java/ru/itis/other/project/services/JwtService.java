package ru.itis.other.project.services;

import org.springframework.security.core.userdetails.UserDetails;
import ru.itis.other.project.models.User;

public interface JwtService {

    String getToken(User user);

    UserDetails parseToken(String token);
}
