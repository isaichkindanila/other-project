package ru.itis.other.project.services;

import ru.itis.other.project.dto.FileDto;
import ru.itis.other.project.dto.LoadFileDto;
import ru.itis.other.project.dto.UploadFileDto;
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
    List<String> getTokenList(String token) throws WrongEntityTypeException;
}
