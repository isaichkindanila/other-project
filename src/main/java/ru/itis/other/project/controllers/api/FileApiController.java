package ru.itis.other.project.controllers.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.other.project.dto.storage.FullFileInfoDto;
import ru.itis.other.project.dto.storage.LoadFileDto;
import ru.itis.other.project.dto.storage.UploadFileDto;
import ru.itis.other.project.services.interfaces.FileService;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileApiController {

    private final FileService fileService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> upload(@RequestBody UploadFileDto dto) {
        fileService.save(dto);

        return ResponseEntity.created(URI.create("/api/files/" + dto.getFileToken())).build();
    }

    @GetMapping("/{token}")
    @PreAuthorize("isAuthenticated()")
    public FullFileInfoDto getInfo(@PathVariable String token) {
        return fileService.getInfo(token);
    }

    @PostMapping("/{token}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> download(@PathVariable String token, @RequestBody LoadFileDto dto) {
        var file = fileService.load(token, dto);

        var headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("inline")
                .filename(file.getName(), StandardCharsets.UTF_8)
                .build());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(file.getMimeType()))
                .contentLength(file.getLength())
                .body(file.getResource());
    }
}
