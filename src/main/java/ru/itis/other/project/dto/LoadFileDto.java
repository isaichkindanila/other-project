package ru.itis.other.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = "key")
@NoArgsConstructor
@AllArgsConstructor
public class LoadFileDto {
    private String token;
    private String key;
}
