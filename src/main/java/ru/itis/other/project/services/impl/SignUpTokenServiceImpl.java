package ru.itis.other.project.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.other.project.dto.SignUpTokenDto;
import ru.itis.other.project.models.SignUpToken;
import ru.itis.other.project.models.User;
import ru.itis.other.project.repositories.SignUpTokenRepository;
import ru.itis.other.project.repositories.UserRepository;
import ru.itis.other.project.services.SignUpTokenService;
import ru.itis.other.project.services.TokenGeneratorService;
import ru.itis.other.project.util.exceptions.TokenNotFoundException;

@Service
@RequiredArgsConstructor
class SignUpTokenServiceImpl implements SignUpTokenService {

    private final UserRepository userRepository;
    private final SignUpTokenRepository tokenRepository;
    private final TokenGeneratorService generatorService;

    @Value("${token.signUp.length}")
    private int tokenLength;

    @Override
    @Transactional
    public SignUpTokenDto createTokenFor(User user) {
        var token = tokenRepository.save(SignUpToken.builder()
                .token(generatorService.generateStringToken(tokenLength))
                .user(user)
                .state(SignUpToken.State.NOT_USED)
                .build());

        return new SignUpTokenDto(token.getToken());
    }

    @Override
    @Transactional
    public boolean confirm(String token) {
        var signUpToken = tokenRepository.find(token).orElseThrow(
                () -> new TokenNotFoundException(token)
        );

        if (signUpToken.getState() == SignUpToken.State.NOT_USED) {
            var user = signUpToken.getUser();

            if (user.getState() != User.State.NOT_CONFIRMED) {
                return false;
            }

            user.setState(User.State.OK);
            userRepository.save(user);

            signUpToken.setState(SignUpToken.State.USED);
            tokenRepository.save(signUpToken);

            return true;
        } else {
            return false;
        }
    }
}
