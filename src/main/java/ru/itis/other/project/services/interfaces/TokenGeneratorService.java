package ru.itis.other.project.services.interfaces;

public interface TokenGeneratorService {

    String base64UrlEncodedToken(int length);

    String hexEncodedToken(int byteLength);
}
