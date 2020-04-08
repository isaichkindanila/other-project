package ru.itis.other.project.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.other.project.models.StorageEntity;
import ru.itis.other.project.repositories.StorageEntityRepository;
import ru.itis.other.project.services.AuthService;
import ru.itis.other.project.services.EncryptionService;
import ru.itis.other.project.services.StorageService;
import ru.itis.other.project.services.TokenGeneratorService;
import ru.itis.other.project.util.exceptions.*;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
class StorageServiceImpl implements StorageService {

    private final TokenGeneratorService tokenGeneratorService;
    private final EncryptionService encryptionService;
    private final AuthService authService;

    private final StorageEntityRepository entityRepository;

    @Override
    @Transactional
    public StorageEntity find(String token) {
        return entityRepository.findByToken(token).orElseThrow(
                () -> new EntityNotFoundException(token)
        );
    }

    @Override
    @Transactional
    public StorageEntity findAndCheck(String token, StorageEntity.Type type) {
        var entity = find(token);

        var user = authService.getUser();
        var owner = entity.getUser();

        if (!user.equals(owner)) {
            throw new AccessDeniedException("it's not yours >:(");
        }

        switch (type) {
            case FILE:
                if (entity.getFileInfo() == null)
                    throw WrongEntityTypeException.expectedFile();

                break;

            case DIRECTORY:
                if (entity.getFileInfo() != null)
                    throw WrongEntityTypeException.expectedDirectory();

                break;
        }

        return entity;
    }

    @Override
    @Transactional
    public StorageEntity findAndCheckSignature(String token, StorageEntity.Type type, String key) {
        var entity = findAndCheck(token, type);
        var signature = encryptionService.signature(token, key);

        if (!entity.getSignature().equals(signature)) {
            throw new InvalidKeyException();
        }

        return entity;
    }

    @Override
    @Transactional
    public StorageEntity findParentByToken(@Nullable String token) {
        if (token == null) {
            return null;
        } else {
            return findAndCheck(token, StorageEntity.Type.DIRECTORY);
        }
    }

    @Override
    @Transactional
    public StorageEntity save(String key, StorageEntity entity) {
        if (!encryptionService.isValidIV(entity.getToken())) {
            throw new BadRequestException("invalid token: must be hex-encoded 12 bytes");
        }

        if (!encryptionService.isValidKey(key)) {
            throw new BadRequestException("invalid key: must be hex-encoded 32 bytes");
        }

        if (entityRepository.findByToken(entity.getToken()).isPresent()) {
            throw new TokenIsAlreadyTakenException(entity.getToken());
        }

        entity.setSignature(encryptionService.signature(entity.getToken(), key));
        return entityRepository.save(entity);
    }

    @Override
    public List<String> getTokenList(StorageEntity entity) {
        var result = new LinkedList<String>();

        while (entity != null) {
            result.addFirst(entity.getToken());
            entity = entity.getParent();
        }

        return result;
    }

    @Override
    public String generateToken() {
        return tokenGeneratorService.hexEncodedToken(12);
    }
}
