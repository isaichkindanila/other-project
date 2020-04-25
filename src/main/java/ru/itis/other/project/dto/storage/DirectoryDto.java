package ru.itis.other.project.dto.storage;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@Builder
public class DirectoryDto extends RepresentationModel<DirectoryDto> {
    private List<String> tokenList;

    private IndexDirectoryInfoDto self;
    private IndexDirectoryInfoDto parent;

    private List<IndexFileInfoDto> files;
    private List<IndexDirectoryInfoDto> directories;
}
