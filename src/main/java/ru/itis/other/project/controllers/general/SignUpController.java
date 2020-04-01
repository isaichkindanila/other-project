package ru.itis.other.project.controllers.general;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.other.project.dto.SignUpDto;
import ru.itis.other.project.services.AuthService;
import ru.itis.other.project.services.SignUpService;
import ru.itis.other.project.util.exceptions.EmailAlreadyTakenException;

@Controller
@RequestMapping("/signUp")
@AllArgsConstructor
public class SignUpController {

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
}