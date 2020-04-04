package ru.itis.other.project.repositories;

import ru.itis.other.project.models.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);
}
