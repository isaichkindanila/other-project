package ru.itis.other.project.repositories.interfaces;

import ru.itis.other.project.models.FileInfo;

public interface FileInfoRepository {

    FileInfo save(FileInfo info);
}
