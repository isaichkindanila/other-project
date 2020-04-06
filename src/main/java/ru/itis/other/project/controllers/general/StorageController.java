package ru.itis.other.project.controllers.general;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.other.project.dto.CreateDirectoryDto;
import ru.itis.other.project.services.DirectoryService;
import ru.itis.other.project.services.StorageService;
import ru.itis.other.project.util.exceptions.WrongEntityTypeException;

@Controller
@RequestMapping("/storage")
@AllArgsConstructor
public class StorageController {

    private final StorageService storageService;
    private final DirectoryService directoryService;

    @GetMapping
    public String getRoot(ModelMap map) {
        return getDirectory(null, map);
    }

    @GetMapping("/{token}")
    public String getDirectory(@PathVariable String token, ModelMap map) {
        try {
            map.put("dir", directoryService.get(token));
            map.put("dirToken", storageService.generateToken());
            map.put("fileToken", storageService.generateToken());

            return "storage";
        } catch (WrongEntityTypeException e) {
            return "redirect:/files/" + token;
        }
    }

    @PostMapping
    public String createRootDirectory(CreateDirectoryDto dto) {
        return createDirectory(null, dto);
    }

    @PostMapping("/{token}")
    public String createDirectory(@PathVariable String token, CreateDirectoryDto dto) {
        directoryService.create(dto);
        return "redirect:/storage" + ((token == null) ? "" : ("/" + token));
    }
}
