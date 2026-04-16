package org.palmadae.donortrack.dto.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ChangeEmailDto {
    @NotBlank
    @Email
    private String newEmail;
}
