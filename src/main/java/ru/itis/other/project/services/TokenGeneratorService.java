package ru.itis.other.project.services;

public interface TokenGeneratorService {

    String base64UrlEncodedToken(int length);

    String hexEncodedToken(int byteLength);
}
