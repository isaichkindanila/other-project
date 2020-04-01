package ru.itis.other.project.repositories;

import ru.itis.other.project.models.SignUpToken;

import java.util.Optional;

public interface SignUpTokenRepository {

    Optional<SignUpToken> find(String token);

    SignUpToken save(SignUpToken token);
}
