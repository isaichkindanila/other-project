package ru.itis.other.project.dto.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = "key")
@NoArgsConstructor
@AllArgsConstructor
public class CreateDirectoryDto {
    private String name;
    private String token;
    private String key;
}
