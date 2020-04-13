package ru.itis.other.project.dto.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullFileInfoDto {
    private String name;
    private String token;
    private String mimeType;
    private long size;
    private List<String> tokens;
}
