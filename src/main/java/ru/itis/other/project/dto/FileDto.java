package ru.itis.other.project.dto;

import lombok.*;
import org.springframework.core.io.Resource;

@Data
@ToString(exclude = "resource")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private long length;
    private String name;
    private String mimeType;
    private Resource resource;
}
