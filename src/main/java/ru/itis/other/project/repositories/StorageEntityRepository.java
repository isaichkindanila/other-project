package ru.itis.other.project.repositories;

import org.springframework.lang.Nullable;
import ru.itis.other.project.models.StorageEntity;
import ru.itis.other.project.models.User;

import java.util.List;
import java.util.Optional;

public interface StorageEntityRepository {

    Optional<StorageEntity> findByToken(String token);

    List<StorageEntity> findByParentAndUser(@Nullable StorageEntity parent, User user);

    StorageEntity save(StorageEntity entity);
}
