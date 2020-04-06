package ru.itis.other.project.services.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.InputStreamResource;
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

@Service
@AllArgsConstructor
class FileServiceImpl implements FileService {

    private final AuthService authService;
    private final StorageService storageService;
    private final EncryptionService encryptionService;

    private final FileInfoRepository fileInfoRepository;
    private final FileStorageRepository fileStorageRepository;
    private final StorageEntityRepository storageEntityRepository;

    @Override
    @SneakyThrows
    @Transactional
    public void save(UploadFileDto dto) {
        var input = dto.getMultipartFile().getInputStream();
        var key = dto.getKey();
        var iv = dto.getFileToken();

        try(var encrypted = encryptionService.encrypt(input, key, iv)) {
            final StorageEntity parent;

            if (dto.getParentToken() == null) {
                parent = null;
            } else {
                parent = storageService.findAndCheck(dto.getParentToken(), StorageEntity.Type.DIRECTORY);
            }

            var fileInfo = fileInfoRepository.save(FileInfo.builder()
                    .size(dto.getMultipartFile().getSize())
                    .mimeType(dto.getMultipartFile().getContentType())
                    .build());

            storageEntityRepository.save(StorageEntity.builder()
                    .name(dto.getMultipartFile().getOriginalFilename())
                    .token(dto.getFileToken())
                    .parent(parent)
                    .fileInfo(fileInfo)
                    .user(authService.getUser())
                    .signature(encryptionService.signature(dto.getFileToken(), dto.getKey()))
                    .build());

            fileStorageRepository.save(encrypted, dto.getFileToken());
        }
    }

    @Override
    @Transactional
    public FileDto load(LoadFileDto dto) {
        var entity = storageService.findAndCheckSignature(dto.getToken(), StorageEntity.Type.FILE, dto.getKey());
        var info = entity.getFileInfo();

        var input = fileStorageRepository.load(dto.getToken());
        var decrypted = encryptionService.decrypt(input, dto.getKey(), dto.getToken());

        return FileDto.builder()
                .length(info.getSize())
                .mimeType(info.getMimeType())
                .name(entity.getName())
                .resource(new InputStreamResource(decrypted))
                .build();
    }

    @Override
    public TokenListDto getTokenList(String token) {
        var entity = storageService.findAndCheck(token, StorageEntity.Type.FILE);

        return storageService.getTokenList(entity);
    }
}
