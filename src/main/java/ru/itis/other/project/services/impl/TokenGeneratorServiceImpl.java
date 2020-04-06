package ru.itis.other.project.services.impl;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Component;
import ru.itis.other.project.services.TokenGeneratorService;

import java.security.SecureRandom;

@Component
class TokenGeneratorServiceImpl implements TokenGeneratorService {

    private final SecureRandom random = new SecureRandom();

    @Override
    public String generateBase64Token(int tokenLength) {
        var bytes = (tokenLength % 4 == 0)
                ? new byte[3 * tokenLength / 4]
                : new byte[3 * (1 + tokenLength / 4)];

        random.nextBytes(bytes);
        var token = Base64.toBase64String(bytes);

        return token.substring(0, tokenLength);
    }

    @Override
    public String generateHexToken(int bytes) {
        var b = new byte[bytes];
        random.nextBytes(b);

        return Hex.toHexString(b);
    }
}
