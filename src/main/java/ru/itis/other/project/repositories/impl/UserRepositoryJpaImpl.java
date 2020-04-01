package ru.itis.other.project.repositories.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.other.project.models.User;
import ru.itis.other.project.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
@Transactional
class UserRepositoryJpaImpl implements UserRepository {

    private static final String FIND_EMAIL =
            "select u from User u where u.email = :email";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(FIND_EMAIL, User.class);
        query.setParameter("email", email);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            // insert
            entityManager.persist(user);
        } else {
            // update
            entityManager.merge(user);
        }
    }
}
