package org.palmadae.donortrack.dto.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.palmadae.donortrack.entity.enums.BloodType;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ChangeBloodTypeDto {
    @NotBlank
    private BloodType bloodType;
}
