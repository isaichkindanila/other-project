package ru.itis.other.project.services;

import org.springframework.lang.Nullable;
import ru.itis.other.project.dto.CreateDirectoryDto;
import ru.itis.other.project.dto.DirectoryDto;
import ru.itis.other.project.util.exceptions.WrongEntityTypeException;

public interface DirectoryService {

    /**
     * @param token directory's token or {@code null} for 'root' directory
     * @throws WrongEntityTypeException if entity is 'file', not 'directory'
     */
    DirectoryDto get(@Nullable String token) throws WrongEntityTypeException;

    void create(CreateDirectoryDto dto);
}
