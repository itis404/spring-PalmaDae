package org.palmadae.donortrack.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
public class UserDto {
    @NotBlank(message = "Имя пользователя не может быть пустое")
    @Size(min = 3, max = 16, message = "Имя пользователя должно быть не короче 3 и не длинее 16 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Имя пользователя может содержать лишь цифры и латинские буквы")
    private String username;


    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, max = 100, message = "Пароль не должен быть короче 6 и длинее 100 символов")
    private String password;

    @NotBlank(message = "Пожалуйста подтвердите ваш пароль")
    private String passCorrect;

    @NotBlank(message = "Почта не может пустой")
    @Email(message = "Пожалуйста введите корректную почту")
    private String email;
}
