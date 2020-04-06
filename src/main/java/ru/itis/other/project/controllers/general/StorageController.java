package ru.itis.other.project.controllers.general;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/storage")
@AllArgsConstructor
public class StorageController {

    @GetMapping
    public String getRoot() {
        return "storage";
    }

    @GetMapping("/{token}")
    public String getDirectory(@PathVariable String token) {
        return "storage";
    }
}
