package ru.itis.other.project.services.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.other.project.dto.FileDto;
import ru.itis.other.project.dto.LoadFileDto;
import ru.itis.other.project.dto.UploadFileDto;
import ru.itis.other.project.models.FileInfo;
import ru.itis.other.project.models.StorageEntity;
import ru.itis.other.project.repositories.FileInfoRepository;
import ru.itis.other.project.repositories.FileStorageRepository;
import ru.itis.other.project.repositories.StorageEntityRepository;
import ru.itis.other.project.services.AuthService;
import ru.itis.other.project.services.EncryptionService;
import ru.itis.other.project.services.FileService;

@SuppressWarnings("Convert2MethodRef")
@Service
@AllArgsConstructor
class FileServiceImpl implements FileService {

    private final AuthService authService;
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
            // TODO: throw 'parent not found' exception
            parent = storageEntityRepository.findByToken(dto.getParentToken())
                    .orElseThrow(() -> new RuntimeException());

            if (parent.getFileInfo() != null) {
                // TODO: throw 'not a directory' exception
                throw new RuntimeException();
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
        // TODO: throw 'entity not found' exception
        var storageEntity = storageEntityRepository.findByToken(dto.getToken())
                .orElseThrow(() -> new RuntimeException());

        if (!storageEntity.getUser().getId().equals(authService.getUser().getId())) {
            // TODO: throw 'access denied' exception
            throw new RuntimeException();
        }

        var info = storageEntity.getFileInfo();
        if (info == null) {
            // TODO: throw 'not a file' exception
            throw new RuntimeException();
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
}
