package ru.itis.other.project.dto.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.other.project.models.StorageEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexFileInfoDto {
    private String name;
    private String token;
    private long size;

    public static IndexFileInfoDto from(StorageEntity file) {
        return IndexFileInfoDto.builder()
                .name(file.getName())
                .token(file.getToken())
                .size(file.getFileInfo().getSize())
                .build();
    }
}
