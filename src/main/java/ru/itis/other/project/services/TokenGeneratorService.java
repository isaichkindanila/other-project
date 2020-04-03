package ru.itis.other.project.services;

public interface TokenGeneratorService {

    String generateStringToken(int length);

    byte[] generateRawToken(int length);
}
