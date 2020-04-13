package ru.itis.other.project.repositories.impl;

import org.springframework.stereotype.Repository;
import ru.itis.other.project.models.FileInfo;
import ru.itis.other.project.repositories.interfaces.FileInfoRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FileInfoRepositoryJpaImpl implements FileInfoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public FileInfo save(FileInfo info) {
        return entityManager.merge(info);
    }
}
