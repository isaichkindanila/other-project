package ru.itis.other.project.repositories;

import ru.itis.other.project.models.StorageEntity;

import java.util.Optional;

public interface StorageEntityRepository {

    Optional<StorageEntity> findByToken(String token);

    StorageEntity save(StorageEntity entity);
}
