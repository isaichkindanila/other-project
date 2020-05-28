package ru.itis.other.project.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@ToString(exclude = "password")
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {
    @Email(message = "{validation.email}")
    private String email;

    @NotEmpty(message = "{validation.password}")
    private String password;
}
