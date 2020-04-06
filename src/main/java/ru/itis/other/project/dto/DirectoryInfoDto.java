package ru.itis.other.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.other.project.models.StorageEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectoryInfoDto {
    String name;
    String token;

    public static DirectoryInfoDto from(StorageEntity directory) {
        return new DirectoryInfoDto(directory.getName(), directory.getToken());
    }
}
