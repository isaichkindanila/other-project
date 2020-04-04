package ru.itis.other.project.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.other.project.dto.SignInDto;
import ru.itis.other.project.models.User;
import ru.itis.other.project.repositories.UserRepository;
import ru.itis.other.project.services.SignInService;

@Service
@AllArgsConstructor
class SignInServiceImpl implements SignInService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User signIn(SignInDto dto) throws AccessDeniedException {
        var user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new AccessDeniedException("bad credentials")
        );

        if (user.getState() != User.State.OK) {
            throw new AccessDeniedException("user is not activated");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassHash())) {
            throw new AccessDeniedException("bad credentials");
        }

        return user;
    }
}
