package ru.itis.other.project.services;

public interface TokenGeneratorService {

    String generateBase64Token(int length);

    String generateHexToken(int byteLength);
}
