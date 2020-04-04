package ru.itis.other.project.services;

import ru.itis.other.project.dto.FileDto;
import ru.itis.other.project.dto.LoadFileDto;
import ru.itis.other.project.dto.UploadFileDto;

public interface FileService {

    void save(UploadFileDto dto);

    FileDto load(LoadFileDto dto);
}
