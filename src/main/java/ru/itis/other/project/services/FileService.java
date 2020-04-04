package ru.itis.other.project.services;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.other.project.dto.FileDto;
import ru.itis.other.project.util.annotations.DoNotLog;

public interface FileService {

    void save(String token, @DoNotLog String key, MultipartFile file);

    FileDto load(String token, @DoNotLog String key);
}
