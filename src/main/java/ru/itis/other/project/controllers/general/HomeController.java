package ru.itis.other.project.controllers.general;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    @PreAuthorize("permitAll()")
    public String home() {
        return "home";
    }

    @GetMapping("/auth")
    @PreAuthorize("isAuthenticated()")
    public String homeAuth() {
        return "home";
    }
}
