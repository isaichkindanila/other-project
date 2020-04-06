package ru.itis.other.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.other.project.models.StorageEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoDto {
    private String name;
    private String token;
    private long size;

    public static FileInfoDto from(StorageEntity file) {
        return FileInfoDto.builder()
                .name(file.getName())
                .token(file.getToken())
                .size(file.getFileInfo().getSize())
                .build();
    }
}
