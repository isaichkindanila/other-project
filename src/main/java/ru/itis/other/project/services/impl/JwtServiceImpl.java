package ru.itis.other.project.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.itis.other.project.dto.JwtDto;
import ru.itis.other.project.models.User;
import ru.itis.other.project.security.details.UserDetailsImpl;
import ru.itis.other.project.services.JwtService;

@Service
class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public JwtDto generateJWT(User user) {
        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("email", user.getEmail())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return new JwtDto(token);
    }

    @Override
    public UserDetails parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            return UserDetailsImpl.builder()
                    .id(Long.parseLong(claims.getSubject()))
                    .email(claims.get("email", String.class))
                    .state(User.State.OK)
                    .build();
        } catch (Exception e) {
            throw new BadCredentialsException("bad token");
        }
    }
}
