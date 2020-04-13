package ru.itis.other.project.services;

import ru.itis.other.project.dto.storage.FileDto;
import ru.itis.other.project.dto.storage.FullFileInfoDto;
import ru.itis.other.project.dto.storage.LoadFileDto;
import ru.itis.other.project.dto.storage.UploadFileDto;
import ru.itis.other.project.util.exceptions.WrongEntityTypeException;

import java.util.List;

public interface FileService {

    void save(UploadFileDto dto);

    /**
     * @throws WrongEntityTypeException if entity is 'directory', not 'file'
     */
    FileDto load(String token, LoadFileDto dto) throws WrongEntityTypeException;

    /**
     * @throws WrongEntityTypeException if entity is 'directory', not 'file'
     */
    FullFileInfoDto getInfo(String token);

    /**
     * @throws WrongEntityTypeException if entity is 'directory', not 'file'
     */
    List<String> getTokenList(String token) throws WrongEntityTypeException;
}
