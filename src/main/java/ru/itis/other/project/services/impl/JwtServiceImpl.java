package ru.itis.other.project.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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

    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JwtServiceImpl(@Value("${jwt.secret}") String secret) {
        algorithm = Algorithm.HMAC256(secret);
        verifier = JWT.require(algorithm).build();
    }

    @Override
    public JwtDto generateJWT(User user) {
        String token = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .sign(algorithm);

        return new JwtDto(token);
    }

    @Override
    public UserDetails parseToken(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);

            return UserDetailsImpl.builder()
                    .id(Long.parseLong(jwt.getSubject()))
                    .email(jwt.getClaim("email").asString())
                    .state(User.State.OK)
                    .build();
        } catch (Exception e) {
            throw new BadCredentialsException("bad token");
        }
    }
}
