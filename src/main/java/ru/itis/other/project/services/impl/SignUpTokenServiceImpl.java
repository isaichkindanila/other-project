package ru.itis.other.project.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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
public class SignUpTokenServiceImpl implements SignUpTokenService {

    private final UserRepository userRepository;
    private final SignUpTokenRepository tokenRepository;
    private final TokenGeneratorService generatorService;

    @Value("${token.signUp.length}")
    private int tokenLength;

    @Override
    public SignUpTokenDto createTokenFor(User user) {
        SignUpToken token = tokenRepository.save(SignUpToken.builder()
                .token(generatorService.generateToken(tokenLength))
                .user(user)
                .state(SignUpToken.State.NOT_USED)
                .build());

        return new SignUpTokenDto(token.getToken());
    }

    @Override
    public boolean confirm(String token) {
        SignUpToken signUpToken = tokenRepository.find(token).orElseThrow(TokenNotFoundException::new);

        if (signUpToken.getState() == SignUpToken.State.NOT_USED) {
            User user = signUpToken.getUser();

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
