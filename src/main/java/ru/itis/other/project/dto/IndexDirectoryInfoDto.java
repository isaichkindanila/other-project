package ru.itis.other.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.other.project.models.StorageEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexDirectoryInfoDto {
    String name;
    String token;

    public static IndexDirectoryInfoDto from(StorageEntity directory) {
        return new IndexDirectoryInfoDto(directory.getName(), directory.getToken());
    }
}
