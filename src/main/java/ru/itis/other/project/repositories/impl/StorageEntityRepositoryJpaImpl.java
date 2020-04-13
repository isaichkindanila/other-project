package ru.itis.other.project.repositories.impl;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.itis.other.project.models.StorageEntity;
import ru.itis.other.project.models.User;
import ru.itis.other.project.repositories.interfaces.StorageEntityRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
class StorageEntityRepositoryJpaImpl implements StorageEntityRepository {

    private static final String FIND_BY_TOKEN =
            "select e from StorageEntity e where e.token = :token";

    private static final String FIND_BY_PARENT_AND_USER =
            "select e from StorageEntity e where e.parent = :parent and e.user = :user";

    private static final String FIND_BY_PARENT_IS_NULL_AND_USER =
            "select e from StorageEntity e where e.parent is null and e.user = :user";

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
    public List<StorageEntity> findByParentAndUser(@Nullable StorageEntity parent, User user) {
        if (parent == null) {
            var query = entityManager.createQuery(FIND_BY_PARENT_IS_NULL_AND_USER, StorageEntity.class);
            query.setParameter("user", user);

            return query.getResultList();
        } else {
            var query = entityManager.createQuery(FIND_BY_PARENT_AND_USER, StorageEntity.class);
            query.setParameter("parent", parent);
            query.setParameter("user", user);

            return query.getResultList();
        }
    }

    @Override
    public StorageEntity save(StorageEntity entity) {
        return entityManager.merge(entity);
    }
}
