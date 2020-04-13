package ru.itis.other.project.controllers.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.other.project.dto.storage.CreateDirectoryDto;
import ru.itis.other.project.dto.storage.DirectoryDto;
import ru.itis.other.project.services.DirectoryService;
import ru.itis.other.project.services.StorageService;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/storage")
@AllArgsConstructor
public class StorageApiController {

    private final StorageService storageService;
    private final DirectoryService directoryService;

    @GetMapping("/token")
    @PreAuthorize("permitAll()")
    public Map<String, String> generateToken() {
        return Map.of("token", storageService.generateToken());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public DirectoryDto getRoot() {
        return getDirectory(null);
    }

    @GetMapping("/{token}")
    @PreAuthorize("isAuthenticated()")
    public DirectoryDto getDirectory(@PathVariable String token) {
        return directoryService.get(token);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createInRoot(@RequestBody CreateDirectoryDto dto) {
        return createWithParent(null, dto);
    }

    @PostMapping("/{token}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createWithParent(@PathVariable String token, @RequestBody CreateDirectoryDto dto) {
        directoryService.create(token, dto);
        return ResponseEntity.created(URI.create("/api/storage/" + dto.getToken())).build();
    }
}
