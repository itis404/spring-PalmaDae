package org.palmadae.donortrack.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserDto {

    @NotBlank(message = "Username in blank")
    @Size(min = 3, max = 16, message = "Username must be between 3 and 16 chars")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username can only contain letters and numbers")
    private String username;


    @NotBlank(message = "Password in blank")
    @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Please confirm your password")
    private String passCorrect;

    @NotBlank(message = "Email in blank")
    @Email(message = "Please provide a valid email address")
    private String email;
}
