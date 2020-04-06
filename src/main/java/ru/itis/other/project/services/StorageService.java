package ru.itis.other.project.services;

import org.springframework.security.access.AccessDeniedException;
import ru.itis.other.project.dto.TokenListDto;
import ru.itis.other.project.models.StorageEntity;
import ru.itis.other.project.util.annotations.DoNotLog;
import ru.itis.other.project.util.exceptions.EntityNotFoundException;
import ru.itis.other.project.util.exceptions.InvalidKeyException;
import ru.itis.other.project.util.exceptions.WrongEntityTypeException;

public interface StorageService {

    /**
     * @throws EntityNotFoundException if entity is not found
     */
    StorageEntity find(String token);

    /**
     * Finds and checks type and user of an entity.
     *
     * @param token entity's token
     * @param type  entity's expected type
     * @return entity
     * @throws EntityNotFoundException  if entity is not found
     * @throws AccessDeniedException    if entity belongs to a different user
     * @throws WrongEntityTypeException if entity has different type
     */
    StorageEntity findAndCheck(String token, StorageEntity.Type type);

    /**
     * Finds and checks type, user and signature of an entity.
     *
     * @param token entity's token
     * @param type  entity's expected type
     * @param key   key to check entity's signature
     * @return entity
     * @throws EntityNotFoundException  if entity is not found
     * @throws AccessDeniedException    if entity belongs to a different user
     * @throws WrongEntityTypeException if entity has different type
     * @throws InvalidKeyException      if signature does not match the key
     */
    StorageEntity findAndCheckSignature(String token, StorageEntity.Type type, @DoNotLog String key);

    TokenListDto getTokenList(StorageEntity entity);

    String generateToken();
}
