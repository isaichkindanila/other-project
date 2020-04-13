package ru.itis.other.project.services.interfaces;

import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import ru.itis.other.project.models.StorageEntity;
import ru.itis.other.project.util.annotations.DoNotLog;
import ru.itis.other.project.util.exceptions.EntityNotFoundException;
import ru.itis.other.project.util.exceptions.InvalidKeyException;
import ru.itis.other.project.util.exceptions.WrongEntityTypeException;

import java.util.List;

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

    /**
     * Finds parent by token. If {@code token} is null, then parent is assumed to be "root".
     *
     * @param token parent's token (can be null if parent is "root")
     * @return entity with matching token (or null if {@code token == null})
     * @throws EntityNotFoundException  if token entity is not found
     * @throws AccessDeniedException    if token entity belongs to a different user
     * @throws WrongEntityTypeException if token entity is not a directory
     */
    @Nullable
    StorageEntity findParentByToken(@Nullable String token);

    /**
     * Checks token and key, then computes signature and persists given {@code entity}.
     */
    StorageEntity save(@DoNotLog String key, StorageEntity entity);

    /**
     * This method <b>MUST</b> be called in the same transaction where {@code entity} was extracted.
     */
    List<String> getTokenList(StorageEntity entity);

    String generateToken();
}
