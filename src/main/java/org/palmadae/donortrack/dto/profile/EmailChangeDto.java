package org.palmadae.donortrack.dto.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
public class EmailChangeDto {
    @NotBlank
    @Email
    private String newEmail;
}
