package ru.itis.other.project.services.impl;

import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.engines.ChaCha7539Engine;
import org.bouncycastle.crypto.io.CipherInputStream;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.DecoderException;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.stereotype.Service;
import ru.itis.other.project.services.interfaces.EncryptionService;
import ru.itis.other.project.util.annotations.NotLoggable;
import ru.itis.other.project.util.exceptions.BadRequestException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String hash(String string) {
        var digest = new SHA256Digest();

        var result = new byte[32];
        var input = string.getBytes(StandardCharsets.UTF_8);

        digest.update(input, 0, input.length);
        digest.doFinal(result, 0);

        return Hex.toHexString(result);
    }

    @Override
    public String signature(String token, String key) {
        return hash(token + key);
    }

    private StreamCipher createCipher(byte[] key, byte[] iv) {
        if (key.length != 32) {
            throw new IllegalArgumentException("invalid key length: expected 32, actual " + key.length);
        }

        if (iv.length != 12) {
            throw new IllegalArgumentException("invalid iv length: expected 12, actual " + iv.length);
        }

        var keyParameter = new KeyParameter(key);
        var parametersWithIV = new ParametersWithIV(keyParameter, iv);

        var cipher = new ChaCha7539Engine();
        cipher.init(true, parametersWithIV);

        return cipher;
    }

    @Override
    @NotLoggable
    public InputStream encrypt(InputStream in, byte[] key, byte[] iv) {
        return new CipherInputStream(in, createCipher(key, iv));
    }

    @Override
    @NotLoggable
    public InputStream decrypt(InputStream in, byte[] key, byte[] iv) {
        return new CipherInputStream(in, createCipher(key, iv));
    }

    private byte[] hexToBytes(String hex, String name, int expectedBytesLength) {
        try {
            var bytes = Hex.decode(hex);

            if (bytes.length != expectedBytesLength) {
                var data = Map.of(
                        "expected", expectedBytesLength,
                        "actual", bytes.length
                );

                throw new BadRequestException(name + " is not correct length", data);
            }

            return bytes;
        } catch (DecoderException e) {
            throw new BadRequestException(name + " is not a hex string");
        }
    }

    @Override
    @NotLoggable
    public InputStream encrypt(InputStream in, String key, String iv) {
        var keyBytes = hexToBytes(key, "key", 32);
        var ivBytes = hexToBytes(iv, "iv", 12);

        return encrypt(in, keyBytes, ivBytes);
    }

    @Override
    @NotLoggable
    public InputStream decrypt(InputStream in, String key, String iv) {
        var keyBytes = hexToBytes(key, "key", 32);
        var ivBytes = hexToBytes(iv, "iv", 12);

        return decrypt(in, keyBytes, ivBytes);
    }

    @Override
    @NotLoggable
    public boolean isValidIV(String iv) {
        try {
            return Hex.decode(iv).length == 12;
        } catch (DecoderException e) {
            return false;
        }
    }

    @Override
    @NotLoggable
    public boolean isValidKey(String key) {
        try {
            return Hex.decode(key).length == 32;
        } catch (DecoderException e) {
            return false;
        }
    }
}
