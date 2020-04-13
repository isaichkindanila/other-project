package ru.itis.other.project.services.impl;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.other.project.dto.storage.FileDto;
import ru.itis.other.project.dto.storage.FullFileInfoDto;
import ru.itis.other.project.dto.storage.LoadFileDto;
import ru.itis.other.project.dto.storage.UploadFileDto;
import ru.itis.other.project.models.FileInfo;
import ru.itis.other.project.models.StorageEntity;
import ru.itis.other.project.repositories.FileInfoRepository;
import ru.itis.other.project.repositories.FileStorageRepository;
import ru.itis.other.project.services.AuthService;
import ru.itis.other.project.services.EncryptionService;
import ru.itis.other.project.services.FileService;
import ru.itis.other.project.services.StorageService;

import java.util.List;

@Service
@AllArgsConstructor
class FileServiceImpl implements FileService {

    private final AuthService authService;
    private final StorageService storageService;
    private final EncryptionService encryptionService;

    private final FileInfoRepository fileInfoRepository;
    private final FileStorageRepository fileStorageRepository;

    @Override
    @SneakyThrows
    @Transactional
    public void save(UploadFileDto dto) {
        var input = dto.getFile().getInputStream();
        var key = dto.getKey();
        var iv = dto.getFileToken();

        try(var encrypted = encryptionService.encrypt(input, key, iv)) {

            var parent = storageService.findParentByToken(dto.getParentToken());

            var fileInfo = fileInfoRepository.save(FileInfo.builder()
                    .size(dto.getFile().getSize())
                    .mimeType(dto.getFile().getContentType())
                    .build());

            storageService.save(dto.getKey(), StorageEntity.builder()
                    .name(dto.getFile().getOriginalFilename())
                    .token(dto.getFileToken())
                    .parent(parent)
                    .fileInfo(fileInfo)
                    .user(authService.getUser())
                    .build());

            fileStorageRepository.save(encrypted, dto.getFileToken());
        }
    }

    @Override
    @Transactional
    public FileDto load(String token, LoadFileDto dto) {
        var entity = storageService.findAndCheckSignature(token, StorageEntity.Type.FILE, dto.getKey());
        var info = entity.getFileInfo();

        var input = fileStorageRepository.load(token);
        var decrypted = encryptionService.decrypt(input, dto.getKey(), token);

        return FileDto.builder()
                .length(info.getSize())
                .mimeType(info.getMimeType())
                .name(entity.getName())
                .resource(new InputStreamResource(decrypted))
                .build();
    }

    @Override
    public FullFileInfoDto getInfo(String token) {
        var entity = storageService.findAndCheck(token, StorageEntity.Type.FILE);
        var info = entity.getFileInfo();

        return FullFileInfoDto.builder()
                .name(entity.getName())
                .token(entity.getToken())
                .size(info.getSize())
                .mimeType(info.getMimeType())
                .tokens(storageService.getTokenList(entity))
                .build();
    }

    @Override
    @Transactional
    public List<String> getTokenList(String token) {
        var entity = storageService.findAndCheck(token, StorageEntity.Type.FILE);

        return storageService.getTokenList(entity);
    }
}
