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
public class DirectoryDto {
    private List<String> tokenList;

    private IndexDirectoryInfoDto self;
    private IndexDirectoryInfoDto parent;

    private List<IndexFileInfoDto> files;
    private List<IndexDirectoryInfoDto> directories;
}
