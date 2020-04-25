package ru.itis.other.project.dto.storage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import ru.itis.other.project.models.StorageEntity;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IndexFileInfoDto extends RepresentationModel<IndexFileInfoDto> {
    private String name;
    private String token;

    public static IndexFileInfoDto from(StorageEntity file) {
        return IndexFileInfoDto.builder()
                .name(file.getName())
                .token(file.getToken())
                .build();
    }
}
