package ru.itis.other.project.repositories;

import ru.itis.other.project.models.FileInfo;

public interface FileInfoRepository {

    FileInfo save(FileInfo info);
}
