package ru.itis.other.project.aspects;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.other.project.dto.SignUpTokenDto;
import ru.itis.other.project.models.User;
import ru.itis.other.project.services.MailService;
import ru.itis.other.project.services.TemplateService;

import java.util.HashMap;
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
            pointcut = "execution(* ru.itis.other.project.services.SignUpTokenService.createTokenFor(..)) && args(user)",
            returning = "token",
            argNames = "token,user"
    )
    public void sendConformationEmail(SignUpTokenDto token, User user) {
        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put("serverURL", serverURL);
        modelMap.put("token", token.getToken());

        String html = templateService.process("email/confirm_sign_up", modelMap);
        mailService.send(user.getEmail(), "Email conformation", html);
    }
}
