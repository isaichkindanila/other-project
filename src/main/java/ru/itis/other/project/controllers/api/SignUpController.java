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
import ru.itis.other.project.services.SignUpService;
import ru.itis.other.project.util.exceptions.EmailAlreadyTakenException;

@RestController
@RequestMapping("/api/signUp")
@AllArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto dto) {
        try {
            signUpService.signUp(dto);
            return ResponseEntity.noContent().build();
        } catch (EmailAlreadyTakenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
