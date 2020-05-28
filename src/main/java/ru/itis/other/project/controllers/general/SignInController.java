package ru.itis.other.project.controllers.general;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.other.project.dto.auth.SignInDto;
import ru.itis.other.project.services.interfaces.AuthService;

@Controller
@RequestMapping("/signIn")
@AllArgsConstructor
public class SignInController {

    private final AuthService authService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public String getSignInPage(Model model) {
        if (authService.isAuthenticated()) {
            return "redirect:/home";
        }

        model.addAttribute("form", new SignInDto());
        return "sign_in";
    }
}
