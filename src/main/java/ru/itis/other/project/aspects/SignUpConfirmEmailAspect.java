package ru.itis.other.project.aspects;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.other.project.dto.auth.SignUpTokenDto;
import ru.itis.other.project.models.User;
import ru.itis.other.project.services.interfaces.MailService;
import ru.itis.other.project.services.interfaces.TemplateService;

import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class SignUpConfirmEmailAspect {

    private final MailService mailService;
    private final TemplateService templateService;

    @Value("${server.url}")
    private String serverURL;

    @AfterReturning(
            pointcut = "execution(* ru.itis.other.project.services.interfaces.SignUpTokenService.createTokenFor(..)) && args(user)",
            returning = "token",
            argNames = "token,user"
    )
    public void sendConformationEmail(SignUpTokenDto token, User user) {
        var modelMap = Map.of(
                "serverURL", serverURL,
                "token", token.getToken()
        );

        var html = templateService.process("email/confirm_sign_up", modelMap);

        mailService.send(user.getEmail(), "Email conformation", html);
    }
}
