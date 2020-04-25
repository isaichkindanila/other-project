package ru.itis.other.project.dto.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import ru.itis.other.project.models.StorageEntity;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor(access = PRIVATE)
public class IndexDirectoryInfoDto extends RepresentationModel<IndexDirectoryInfoDto> {
    private final String name;
    private final String token;

    public static IndexDirectoryInfoDto from(StorageEntity directory) {
        return new IndexDirectoryInfoDto(directory.getName(), directory.getToken());
    }
}
