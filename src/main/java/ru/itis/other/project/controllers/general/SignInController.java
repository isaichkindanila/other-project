package ru.itis.other.project.controllers.general;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.other.project.services.interfaces.AuthService;

@Controller
@RequestMapping("/signIn")
@AllArgsConstructor
public class SignInController {

    private final AuthService authService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public String getSignInPage(@RequestParam(required = false) String error, ModelMap modelMap) {
        if (authService.isAuthenticated()) {
            return "redirect:/home";
        }

        modelMap.addAttribute("error", error != null);

        return "sign_in";
    }
}
