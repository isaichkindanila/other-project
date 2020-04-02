package ru.itis.other.project.controllers.api;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.other.project.dto.SignUpDto;
import ru.itis.other.project.dto.SignUpTokenDto;
import ru.itis.other.project.services.SignUpService;
import ru.itis.other.project.services.SignUpTokenService;
import ru.itis.other.project.util.ApiErrorResponse;

@RestController
@RequestMapping("/api/signUp")
@AllArgsConstructor
public class SignUpApiController {

    private final SignUpService signUpService;
    private final SignUpTokenService signUpTokenService;

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto dto) {
        signUpService.signUp(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/confirm")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> confirm(@RequestBody SignUpTokenDto dto) {
        if (signUpTokenService.confirm(dto.getToken())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ApiErrorResponse(HttpStatus.CONFLICT, "token already used", dto).toResponseEntity();
        }
    }
}
