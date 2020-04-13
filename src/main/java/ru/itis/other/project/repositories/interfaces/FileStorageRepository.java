package ru.itis.other.project.repositories.interfaces;

import java.io.InputStream;

public interface FileStorageRepository {

    void save(InputStream in, String name);

    InputStream load(String name);
}
