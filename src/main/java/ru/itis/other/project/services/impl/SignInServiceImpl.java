package ru.itis.other.project.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.other.project.dto.auth.SignInDto;
import ru.itis.other.project.models.User;
import ru.itis.other.project.models.UserInfo;
import ru.itis.other.project.repositories.UserInfoRepository;
import ru.itis.other.project.repositories.UserRepository;
import ru.itis.other.project.services.SignInService;

@Service
@AllArgsConstructor
@Slf4j
class SignInServiceImpl implements SignInService {

    private final UserRepository userRepository;
    private final UserInfoRepository infoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User signIn(SignInDto dto) throws AccessDeniedException {
        var user = userRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new AccessDeniedException("bad credentials")
        );

        var info = infoRepository.findByUser(user);

        if (info.getState() != UserInfo.State.OK) {
            throw new AccessDeniedException("user is not activated");
        }

        if (!passwordEncoder.matches(dto.getPassword(), info.getPassHash())) {
            throw new AccessDeniedException("bad credentials");
        }

        return user;
    }
}
