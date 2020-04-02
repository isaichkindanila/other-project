package ru.itis.other.project.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.other.project.dto.SignUpDto;
import ru.itis.other.project.models.User;
import ru.itis.other.project.repositories.UserRepository;
import ru.itis.other.project.services.SignUpService;
import ru.itis.other.project.services.SignUpTokenService;
import ru.itis.other.project.util.exceptions.EmailAlreadyTakenException;

@Service
@AllArgsConstructor
class SignUpServiceImpl implements SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SignUpTokenService tokenService;

    @Override
    public void signUp(SignUpDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyTakenException(dto.getEmail());
        }

        User user = userRepository.save(User.builder()
                .email(dto.getEmail())
                .passHash(passwordEncoder.encode(dto.getPassword()))
                .state(User.State.NOT_CONFIRMED)
                .build());

        tokenService.createTokenFor(user);
    }
}
