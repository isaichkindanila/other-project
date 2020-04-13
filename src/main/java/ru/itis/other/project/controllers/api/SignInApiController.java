package ru.itis.other.project.controllers.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.other.project.dto.auth.JwtDto;
import ru.itis.other.project.dto.auth.SignInDto;
import ru.itis.other.project.services.interfaces.JwtService;
import ru.itis.other.project.services.interfaces.SignInService;

@RestController
@RequestMapping("/api/signIn")
@AllArgsConstructor
public class SignInApiController {

    private final SignInService signInService;
    private final JwtService jwtService;

    @PostMapping
    public JwtDto signIn(@RequestBody SignInDto dto) {
        return jwtService.generateJWT(signInService.signIn(dto));
    }
}
