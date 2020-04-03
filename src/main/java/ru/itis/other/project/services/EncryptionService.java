package ru.itis.other.project.services;

import java.io.InputStream;
import java.io.OutputStream;

public interface EncryptionService {

    /**
     * Calculates SHA-256 of a given {@code String} and returns hex string.
     *
     * @param string string to be hashed
     * @return hex string of the hash
     */
    String hash(String string);

    /**
     * Encrypts given {@code OutputStream}.
     *
     * @param out {@code OutputStream} to encrypt
     * @param key secret key, 32 bytes
     * @param iv  initialization vector, 12 bytes
     * @return encrypted {@code OutputStream}
     * @throws IllegalArgumentException if {@code key} or {@code iv} is wrong size
     */
    OutputStream encrypt(OutputStream out, byte[] key, byte[] iv);

    /**
     * Decrypts given {@code InputStream}.
     *
     * @param in  {@code InputStream} to encrypt
     * @param key secret key, 32 bytes
     * @param iv  initialization vector, 12 bytes
     * @return decrypted {@code InputStream}
     * @throws IllegalArgumentException if {@code key} or {@code iv} is wrong size
     */
    InputStream decrypt(InputStream in, byte[] key, byte[] iv);
}
