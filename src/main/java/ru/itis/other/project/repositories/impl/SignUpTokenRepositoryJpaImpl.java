package ru.itis.other.project.repositories.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.other.project.models.SignUpToken;
import ru.itis.other.project.repositories.SignUpTokenRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@Transactional
public class SignUpTokenRepositoryJpaImpl implements SignUpTokenRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SignUpToken> find(String token) {
        return Optional.ofNullable(entityManager.find(SignUpToken.class, token));
    }

    @Override
    public SignUpToken save(SignUpToken token) {
        return entityManager.merge(token);
    }
}
