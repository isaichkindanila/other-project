package ru.itis.other.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString(exclude = "key")
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileDto {
    private String fileToken;
    private String parentToken;
    private String key;
    private MultipartFile multipartFile;
}
