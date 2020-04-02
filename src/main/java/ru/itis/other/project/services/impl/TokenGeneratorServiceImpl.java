package ru.itis.other.project.services.impl;

import org.springframework.stereotype.Component;
import ru.itis.other.project.services.TokenGeneratorService;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class TokenGeneratorServiceImpl implements TokenGeneratorService {

    private final Base64.Encoder encoder = Base64.getUrlEncoder();
    private final SecureRandom random = new SecureRandom();

    @Override
    public String generateToken(int length) {
        var bytes = (length % 4 == 0)
                ? new byte[3 * length / 4]
                : new byte[3 * (1 + length / 4)];

        random.nextBytes(bytes);
        var token = encoder.encodeToString(bytes);

        return token.substring(0, length);
    }
}
