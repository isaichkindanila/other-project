package ru.itis.other.project.repositories.impl;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.itis.other.project.repositories.FileStorageRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

@Repository
public class FileStorageRepositoryLocalStorageImpl implements FileStorageRepository {

    private final File storage;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public FileStorageRepositoryLocalStorageImpl(@Value("@{storage.path}") String storagePath) {
        storage = new File(storagePath);
        storage.mkdirs();
    }

    @Override
    @SneakyThrows
    public void save(InputStream in, String name) {
        var file = new File(storage, name);

        if (file.exists()) {
            throw new IllegalStateException("file '" + name + "' already exists");
        }

        try(var out = new FileOutputStream(file)) {
            in.transferTo(out);
        }
    }

    @Override
    @SneakyThrows
    public InputStream load(String name) {
        var file = new File(storage, name);

        if (!file.exists()) {
            throw new IllegalStateException("file '" + name + "' does not exist");
        }

        return new FileInputStream(file);
    }
}
