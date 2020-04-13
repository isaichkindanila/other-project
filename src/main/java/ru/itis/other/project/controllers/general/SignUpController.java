package ru.itis.other.project.controllers.general;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itis.other.project.dto.auth.SignUpDto;
import ru.itis.other.project.services.interfaces.AuthService;
import ru.itis.other.project.services.interfaces.SignUpService;
import ru.itis.other.project.services.interfaces.SignUpTokenService;
import ru.itis.other.project.util.exceptions.EmailAlreadyTakenException;

@Controller
@RequestMapping("/signUp")
@AllArgsConstructor
public class SignUpController {

    private final SignUpTokenService signUpTokenService;
    private final SignUpService signUpService;
    private final AuthService authService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public String getSignUpPage(@RequestParam(required = false) String error, ModelMap modelMap) {
        if (authService.isAuthenticated()) {
            return "redirect:/home";
        }

        modelMap.addAttribute("error", error != null);

        return "sign_up";
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    public String signUp(SignUpDto dto) {
        try {
            signUpService.signUp(dto);
            return "signed_up";
        } catch (EmailAlreadyTakenException e) {
            return "redirect:/signUp?error";
        }
    }

    @GetMapping("/confirm/{token}")
    public String confirm(@PathVariable String token) {
        if (signUpTokenService.confirm(token)) {
            return "sign_up_confirmed";
        } else {
            return "redirect:/home";
        }
    }
}
