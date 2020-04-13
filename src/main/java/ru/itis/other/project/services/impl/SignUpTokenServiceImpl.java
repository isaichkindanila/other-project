package ru.itis.other.project.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.other.project.dto.auth.SignUpTokenDto;
import ru.itis.other.project.models.SignUpToken;
import ru.itis.other.project.models.User;
import ru.itis.other.project.models.UserInfo;
import ru.itis.other.project.repositories.SignUpTokenRepository;
import ru.itis.other.project.repositories.UserInfoRepository;
import ru.itis.other.project.services.SignUpTokenService;
import ru.itis.other.project.services.TokenGeneratorService;
import ru.itis.other.project.util.exceptions.TokenNotFoundException;

@Service
@RequiredArgsConstructor
class SignUpTokenServiceImpl implements SignUpTokenService {

    private final UserInfoRepository infoRepository;
    private final SignUpTokenRepository tokenRepository;
    private final TokenGeneratorService generatorService;

    @Value("${token.signUp.length}")
    private int tokenLength;

    @Override
    @Transactional
    public SignUpTokenDto createTokenFor(User user) {
        var token = tokenRepository.save(SignUpToken.builder()
                .token(generatorService.base64UrlEncodedToken(tokenLength))
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

        if (signUpToken.getState() == SignUpToken.State.USED) {
            return false;
        }

        var user = signUpToken.getUser();
        var info = infoRepository.findByUser(user);

        info.setState(UserInfo.State.OK);
        infoRepository.save(info);

        signUpToken.setState(SignUpToken.State.USED);
        tokenRepository.save(signUpToken);

        return true;
    }
}
