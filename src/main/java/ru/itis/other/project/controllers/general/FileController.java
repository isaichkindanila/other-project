package ru.itis.other.project.controllers.general;

import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import ru.itis.other.project.dto.storage.LoadFileDto;
import ru.itis.other.project.dto.storage.UploadFileDto;
import ru.itis.other.project.services.interfaces.FileService;
import ru.itis.other.project.util.exceptions.WrongEntityTypeException;

import java.nio.charset.StandardCharsets;

@Controller
@AllArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public String upload(UploadFileDto dto) {
        if (dto.getFile().getOriginalFilename() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "file name is missing");
        }

        if (dto.getFile().getSize() == 0L) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "file is empty");
        }

        fileService.save(dto);

        if (dto.getParentToken() == null) {
            return "redirect:/storage";
        } else {
            return "redirect:/storage/" + dto.getParentToken();
        }
    }

    @GetMapping("/{token}")
    @PreAuthorize("isAuthenticated()")
    public String getDownloadPage(@PathVariable String token, ModelMap map) {
        try {
            map.put("tokens", fileService.getTokenList(token));
            map.put("token", token);

            return "load_file";
        } catch (WrongEntityTypeException e) {
            return "redirect:/storage/" + token;
        }
    }

    @PostMapping("/{token}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> download(@PathVariable String token, LoadFileDto dto) {
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
