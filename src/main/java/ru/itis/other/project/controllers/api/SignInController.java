package ru.itis.other.project.controllers.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.other.project.dto.JwtDto;
import ru.itis.other.project.dto.SignInDto;
import ru.itis.other.project.services.JwtService;
import ru.itis.other.project.services.SignInService;

@RestController
@RequestMapping("/api/signIn")
@AllArgsConstructor
public class SignInController {

    private final SignInService signInService;
    private final JwtService jwtService;

    @PostMapping
    public JwtDto signIn(@RequestBody SignInDto dto) {
        return jwtService.generateJWT(signInService.signIn(dto));
    }
}
