package ru.itis.other.project.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.other.project.dto.CreateDirectoryDto;
import ru.itis.other.project.dto.DirectoryDto;
import ru.itis.other.project.dto.IndexDirectoryInfoDto;
import ru.itis.other.project.dto.IndexFileInfoDto;
import ru.itis.other.project.models.StorageEntity;
import ru.itis.other.project.repositories.StorageEntityRepository;
import ru.itis.other.project.services.AuthService;
import ru.itis.other.project.services.DirectoryService;
import ru.itis.other.project.services.StorageService;
import ru.itis.other.project.util.exceptions.WrongEntityTypeException;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DirectoryServiceImpl implements DirectoryService {

    private final StorageService storageService;
    private final AuthService authService;

    private final StorageEntityRepository entityRepository;

    private Pair<List<IndexDirectoryInfoDto>, List<IndexFileInfoDto>> findByParent(@Nullable StorageEntity parent) {
        var children = entityRepository.findByParentAndUser(parent, authService.getUser());

        var dirs = new ArrayList<IndexDirectoryInfoDto>();
        var files = new ArrayList<IndexFileInfoDto>();

        for (var child : children) {
            if (child.getFileInfo() == null) {
                dirs.add(IndexDirectoryInfoDto.from(child));
            } else {
                files.add(IndexFileInfoDto.from(child));
            }
        }

        return Pair.of(dirs, files);
    }

    @Override
    @Transactional
    public DirectoryDto get(@Nullable String token) throws WrongEntityTypeException {
        if (token == null) {
            var dirsAndFiles = findByParent(null);

            return DirectoryDto.builder()
                    .tokenList(List.of())
                    .self(null)
                    .parent(null)
                    .directories(dirsAndFiles.getFirst())
                    .files(dirsAndFiles.getSecond())
                    .build();
        } else {
            var directory = storageService.findAndCheck(token, StorageEntity.Type.DIRECTORY);
            var parent = directory.getParent();

            var selfDto = IndexDirectoryInfoDto.from(directory);
            var parentDto = (parent == null) ? null : IndexDirectoryInfoDto.from(parent);

            var dirsAndFiles = findByParent(directory);

            return DirectoryDto.builder()
                    .tokenList(storageService.getTokenList(directory))
                    .self(selfDto)
                    .parent(parentDto)
                    .directories(dirsAndFiles.getFirst())
                    .files(dirsAndFiles.getSecond())
                    .build();
        }
    }

    @Override
    public void create(@Nullable String parentToken, CreateDirectoryDto dto) {
        var parent = storageService.findParentByToken(parentToken);

        storageService.save(dto.getKey(), StorageEntity.builder()
                .name(dto.getName())
                .user(authService.getUser())
                .fileInfo(null)
                .parent(parent)
                .token(dto.getToken())
                .build());
    }
}
