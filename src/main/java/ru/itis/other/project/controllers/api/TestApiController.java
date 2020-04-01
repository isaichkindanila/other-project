package ru.itis.other.project.controllers.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestApiController {

    @GetMapping
    @PreAuthorize("permitAll()")
    public String test() {
        return "Hello, world!";
    }

    @GetMapping("/auth")
    @PreAuthorize("isAuthenticated()")
    public String testAuth() {
        return "Hello, world!";
    }
}
