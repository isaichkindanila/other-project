package ru.itis.other.project.services.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.ChaCha7539Engine;
import org.bouncycastle.crypto.io.CipherInputStream;
import org.bouncycastle.crypto.io.CipherOutputStream;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.springframework.stereotype.Service;
import ru.itis.other.project.services.EncryptionService;
import ru.itis.other.project.util.annotations.NotLoggable;

import java.io.InputStream;
import java.io.OutputStream;

@Service
class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String hash(String string) {
        return DigestUtils.sha256Hex(string);
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
    public OutputStream encrypt(OutputStream out, byte[] key, byte[] iv) {
        return new CipherOutputStream(out, createCipher(key, iv));
    }

    @Override
    @NotLoggable
    public InputStream decrypt(InputStream in, byte[] key, byte[] iv) {
        return new CipherInputStream(in, createCipher(key, iv));
    }
}
