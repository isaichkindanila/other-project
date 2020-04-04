package ru.itis.other.project.repositories.impl;

import org.springframework.stereotype.Repository;
import ru.itis.other.project.models.StorageEntity;
import ru.itis.other.project.repositories.StorageEntityRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
class StorageEntityRepositoryJpaImpl implements StorageEntityRepository {

    private static final String FIND_BY_TOKEN =
            "select e from StorageEntity e where token = :token";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<StorageEntity> findByToken(String token) {
        var query = entityManager.createQuery(FIND_BY_TOKEN, StorageEntity.class);
        query.setParameter("token", token);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public StorageEntity save(StorageEntity entity) {
        return entityManager.merge(entity);
    }
}
