package ru.itis.other.project.services.impl;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;
import ru.itis.other.project.services.TokenGeneratorService;

import java.security.SecureRandom;
import java.util.Base64;

@Component
class TokenGeneratorServiceImpl implements TokenGeneratorService {

    private final SecureRandom random = new SecureRandom();
    private final Base64.Encoder encoder = Base64.getUrlEncoder();

    @Override
    public String base64UrlEncodedToken(int tokenLength) {
        var bytes = (tokenLength % 4 == 0)
                ? new byte[3 * tokenLength / 4]
                : new byte[3 * (1 + tokenLength / 4)];

        random.nextBytes(bytes);
        var token = encoder.encodeToString(bytes);

        return token.substring(0, tokenLength);
    }

    @Override
    public String hexEncodedToken(int bytes) {
        var b = new byte[bytes];
        random.nextBytes(b);

        return Hex.toHexString(b);
    }
}
