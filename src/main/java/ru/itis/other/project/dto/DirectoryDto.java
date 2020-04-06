package ru.itis.other.project.dto;

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

    private DirectoryInfoDto self;
    private DirectoryInfoDto parent;

    private List<FileInfoDto> files;
    private List<DirectoryInfoDto> directories;
}
