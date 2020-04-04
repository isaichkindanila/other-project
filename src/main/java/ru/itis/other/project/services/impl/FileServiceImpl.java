package ru.itis.other.project.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.other.project.dto.FileDto;
import ru.itis.other.project.services.FileService;

@Service
@AllArgsConstructor
class FileServiceImpl implements FileService {

    @Override
    public void save(String token, String key, MultipartFile file) {
        // TODO: implement method
    }

    @Override
    public FileDto load(String token, String key) {
        // TODO: implement method
        return null;
    }
}
