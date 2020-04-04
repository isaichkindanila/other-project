package ru.itis.other.project.repositories.impl;

import org.springframework.stereotype.Repository;
import ru.itis.other.project.models.User;
import ru.itis.other.project.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
class UserRepositoryJpaImpl implements UserRepository {

    private static final String FIND_EMAIL =
            "select u from User u where u.email = :email";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        var query = entityManager.createQuery(FIND_EMAIL, User.class);
        query.setParameter("email", email);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public User save(User user) {
        return entityManager.merge(user);
    }
}
