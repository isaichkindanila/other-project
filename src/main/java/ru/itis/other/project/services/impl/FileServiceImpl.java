package ru.itis.other.project.services.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.other.project.dto.FileDto;
import ru.itis.other.project.dto.LoadFileDto;
import ru.itis.other.project.dto.TokenListDto;
import ru.itis.other.project.dto.UploadFileDto;
import ru.itis.other.project.models.FileInfo;
import ru.itis.other.project.models.StorageEntity;
import ru.itis.other.project.repositories.FileInfoRepository;
import ru.itis.other.project.repositories.FileStorageRepository;
import ru.itis.other.project.repositories.StorageEntityRepository;
import ru.itis.other.project.services.AuthService;
import ru.itis.other.project.services.EncryptionService;
import ru.itis.other.project.services.FileService;
import ru.itis.other.project.services.StorageService;
import ru.itis.other.project.util.exceptions.WrongEntityTypeException;

@Service
@AllArgsConstructor
class FileServiceImpl implements FileService {

    private final AuthService authService;
    private final StorageService storageService;
    private final EncryptionService encryptionService;

    private final FileInfoRepository fileInfoRepository;
    private final FileStorageRepository fileStorageRepository;
    private final StorageEntityRepository storageEntityRepository;

    private byte[] hexToBytes(String hex, String name, int expectedBytesLength) {
        try {
            var bytes = Hex.decodeHex(hex);

            if (bytes.length != expectedBytesLength) {
                // TODO: throw '{name} is not correct length' exception
                throw new RuntimeException();
            }

            return bytes;
        } catch (DecoderException e) {
            // TODO: throw '{name} is not a hex string' exception
            throw new RuntimeException();
        }
    }

    private String getSignature(String token, String key) {
        return encryptionService.hash(token + key);
    }

    @Override
    @SneakyThrows
    @Transactional
    public void save(UploadFileDto dto) {
        var keyBytes = hexToBytes(dto.getKey(), "key", 32);
        var ivBytes = hexToBytes(dto.getFileToken(), "token", 12);

        final StorageEntity parent;

        if (dto.getParentToken() == null) {
            parent = null;
        } else {
            parent = storageService.find(dto.getParentToken());

            if (parent.getFileInfo() != null) {
                throw new WrongEntityTypeException("directory", "file");
            }
        }

        var fileInfo = fileInfoRepository.save(FileInfo.builder()
                .name(dto.getMultipartFile().getOriginalFilename())
                .size(dto.getMultipartFile().getSize())
                .mimeType(dto.getMultipartFile().getContentType())
                .build());

        storageEntityRepository.save(StorageEntity.builder()
                .token(dto.getFileToken())
                .parent(parent)
                .fileInfo(fileInfo)
                .user(authService.getUser())
                .signature(getSignature(dto.getFileToken(), dto.getKey()))
                .build());

        var input = dto.getMultipartFile().getInputStream();
        var encrypted = encryptionService.encrypt(input, keyBytes, ivBytes);

        fileStorageRepository.save(encrypted, dto.getFileToken());
    }

    @Override
    @Transactional
    public FileDto load(LoadFileDto dto) {
        var storageEntity = storageService.find(dto.getToken());

        if (!storageEntity.getUser().getId().equals(authService.getUser().getId())) {
            throw new AccessDeniedException("it's not yours >:(");
        }

        var info = storageEntity.getFileInfo();

        if (info == null) {
            throw new WrongEntityTypeException("file", "directory");
        }

        var signature = getSignature(dto.getToken(), dto.getKey());

        if (!signature.equals(storageEntity.getSignature())) {
            // TODO: throw 'invalid key' exception
            throw new RuntimeException();
        }

        var keyBytes = hexToBytes(dto.getKey(), "key", 32);
        var ivBytes = hexToBytes(dto.getToken(), "token", 12);

        var input = fileStorageRepository.load(dto.getToken());
        var decrypted = encryptionService.decrypt(input, keyBytes, ivBytes);

        return FileDto.builder()
                .length(info.getSize())
                .mimeType(info.getMimeType())
                .name(info.getName())
                .resource(new InputStreamResource(decrypted))
                .build();
    }

    @Override
    public TokenListDto getTokenList(String token) {
        var entity = storageService.find(token);

        if (!entity.getUser().getId().equals(authService.getUser().getId())) {
            throw new AccessDeniedException("it's not yours >:(");
        }

        if (entity.getFileInfo() != null) {
            throw new WrongEntityTypeException("file", "directory");
        }

        return storageService.getTokenList(entity);
    }
}
