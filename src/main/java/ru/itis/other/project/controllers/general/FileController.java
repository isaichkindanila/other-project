package ru.itis.other.project.controllers.general;

import lombok.AllArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.other.project.dto.LoadFileDto;
import ru.itis.other.project.dto.UploadFileDto;
import ru.itis.other.project.services.FileService;

import java.nio.charset.StandardCharsets;

@Controller
@AllArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @GetMapping
    public String fileList(ModelMap modelMap) {
        // TODO: find user's files
        return "files";
    }

    @PostMapping
    public String upload(UploadFileDto dto) {
        fileService.save(dto);
        return "redirect:/files";
    }

    @GetMapping("/{token}")
    public String getDownloadPage(@PathVariable String token, ModelMap map) {
        // TODO: a lot of stuff
        return "load_file";
    }

    @PostMapping("/{token}")
    public ResponseEntity<?> download(@PathVariable String token, LoadFileDto dto) {
        if (!token.equals(dto.getToken())) {
            return ResponseEntity.badRequest().build();
        }

        var file = fileService.load(dto);

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
