package org.palmadae.donortrack.dto.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Data
public class PasswordChangeDto {
    @NotBlank
    private String oldPassword;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, max = 100, message = "Пароль не должен быть короче 6 и длинее 100 символов")
    private String newPassword;

    @NotBlank(message = "Пожалуйста подтвердите ваш пароль")
    private String confirmPassword;
}
