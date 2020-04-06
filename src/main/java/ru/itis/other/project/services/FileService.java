package ru.itis.other.project.services;

import ru.itis.other.project.dto.FileDto;
import ru.itis.other.project.dto.LoadFileDto;
import ru.itis.other.project.dto.TokenListDto;
import ru.itis.other.project.dto.UploadFileDto;
import ru.itis.other.project.util.exceptions.WrongEntityTypeException;

public interface FileService {

    void save(UploadFileDto dto);

    /**
     * @throws WrongEntityTypeException if entity is 'directory', not 'file'
     */
    FileDto load(LoadFileDto dto) throws WrongEntityTypeException;

    /**
     * @throws WrongEntityTypeException if entity is 'directory', not 'file'
     */
    TokenListDto getTokenList(String token) throws WrongEntityTypeException;
}
