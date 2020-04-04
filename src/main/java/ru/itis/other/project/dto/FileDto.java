package ru.itis.other.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private long length;
    private String name;
    private String mimeType;
    private Resource resource;
}
