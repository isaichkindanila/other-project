package ru.itis.other.project.services;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.other.project.dto.FileDto;

public interface FileService {

    void save(String token, String key, MultipartFile file);

    FileDto load(String token, String key);
}
