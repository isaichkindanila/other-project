package ru.itis.other.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(exclude = "password")
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {
    private String email;
    private String password;
}
