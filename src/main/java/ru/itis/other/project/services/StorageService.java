package ru.itis.other.project.services;

import ru.itis.other.project.dto.TokenListDto;
import ru.itis.other.project.models.StorageEntity;
import ru.itis.other.project.util.exceptions.EntityNotFoundException;

public interface StorageService {

    /**
     * @throws EntityNotFoundException if entity is not found
     */
    StorageEntity find(String token) throws EntityNotFoundException;

    TokenListDto getTokenList(StorageEntity entity);
}
