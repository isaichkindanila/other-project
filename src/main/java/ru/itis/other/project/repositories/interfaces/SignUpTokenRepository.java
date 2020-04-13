package ru.itis.other.project.repositories.interfaces;

import ru.itis.other.project.models.SignUpToken;

import java.util.Optional;

public interface SignUpTokenRepository {

    Optional<SignUpToken> find(String token);

    SignUpToken save(SignUpToken token);
}
