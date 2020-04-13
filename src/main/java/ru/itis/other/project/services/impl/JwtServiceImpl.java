package ru.itis.other.project.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.itis.other.project.dto.auth.JwtDto;
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
        var token = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .sign(algorithm);

        return new JwtDto(token);
    }

    @Override
    public UserDetails parseToken(String token) {
        try {
            var jwt = verifier.verify(token);

            var id = Long.parseLong(jwt.getSubject());
            var email = jwt.getClaim("email").asString();

            return new UserDetailsImpl(id, email);
        } catch (Exception e) {
            throw new BadCredentialsException("bad token");
        }
    }
}
