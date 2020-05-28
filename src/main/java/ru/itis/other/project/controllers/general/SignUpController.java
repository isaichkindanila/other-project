package ru.itis.other.project.controllers.general;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.other.project.dto.auth.SignUpDto;
import ru.itis.other.project.services.interfaces.AuthService;
import ru.itis.other.project.services.interfaces.SignUpService;
import ru.itis.other.project.services.interfaces.SignUpTokenService;
import ru.itis.other.project.util.exceptions.EmailAlreadyTakenException;

import javax.validation.Valid;

@Controller
@RequestMapping("/signUp")
@AllArgsConstructor
public class SignUpController {

    private final SignUpTokenService signUpTokenService;
    private final SignUpService signUpService;
    private final AuthService authService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public String getSignUpPage(Model model) {
        if (authService.isAuthenticated()) {
            return "redirect:/home";
        }

        model.addAttribute("form", new SignUpDto());
        return "sign_up";
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    public String signUp(@Valid SignUpDto dto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            try {
                signUpService.signUp(dto);
                return "signed_up";
            } catch (EmailAlreadyTakenException e) {
                return "redirect:/signUp?error";
            }
        }

        model.addAttribute("form", dto);
        return "sign_up";
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
