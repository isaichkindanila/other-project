package ru.itis.other.project.controllers.general;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    @PreAuthorize("permitAll()")
    public String home() {
        return "home";
    }

    @GetMapping("/home/auth")
    @PreAuthorize("isAuthenticated()")
    public String homeAuth() {
        return "home";
    }
}
