package ru.itis.other.project.services;

import ru.itis.other.project.util.annotations.DoNotLog;
import ru.itis.other.project.util.annotations.NotLoggable;

import java.io.InputStream;

public interface EncryptionService {

    /**
     * Calculates SHA-256 of a given {@code String} and returns hex string.
     *
     * @param string string to be hashed
     * @return hex string of the hash
     */
    String hash(@DoNotLog String string);

    /**
     * @return hash-signature of key and token
     */
    String signature(String token, @DoNotLog String key);

    /**
     * Encrypts given {@code InputStream}.
     *
     * @param in  {@code InputStream} to encrypt
     * @param key secret key, 32 bytes
     * @param iv  initialization vector, 12 bytes
     * @return encrypted {@code InputStream}
     * @throws IllegalArgumentException if {@code key} or {@code iv} is wrong size
     */
    @NotLoggable
    InputStream encrypt(InputStream in, byte[] key, byte[] iv);

    /**
     * Encrypts given {@code InputStream}.
     *
     * @param in  {@code InputStream} to encrypt
     * @param key secret key, 32 bytes encoded in hex format
     * @param iv  initialization vector, 12 bytes encoded in hex format
     * @return encrypted {@code InputStream}
     * @throws IllegalArgumentException if {@code key} or {@code iv} is wrong size or not in hex format
     */
    @NotLoggable
    InputStream encrypt(InputStream in, String key, String iv);

    /**
     * Decrypts given {@code InputStream}.
     *
     * @param in  {@code InputStream} to encrypt
     * @param key secret key, 32 bytes
     * @param iv  initialization vector, 12 bytes
     * @return decrypted {@code InputStream}
     * @throws IllegalArgumentException if {@code key} or {@code iv} is wrong size
     */
    @NotLoggable
    InputStream decrypt(InputStream in, byte[] key, byte[] iv);

    /**
     * Decrypts given {@code InputStream}.
     *
     * @param in  {@code InputStream} to encrypt
     * @param key secret key, 32 bytes encoded in hex format
     * @param iv  initialization vector, 12 bytes encoded in hex format
     * @return decrypted {@code InputStream}
     * @throws IllegalArgumentException if {@code key} or {@code iv} is wrong size or not in hex format
     */
    @NotLoggable
    InputStream decrypt(InputStream in, String key, String iv);

    /**
     * Checks if given {@code String} is valid IV (hex string with length of 24).
     */
    @NotLoggable
    boolean isValidIV(String iv);

    /**
     * Checks if given {@code String} is valid key (hex string with length of 64).
     */
    @NotLoggable
    boolean isValidKey(String key);
}
