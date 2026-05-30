package org.palmadae.donortrack.dto.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.palmadae.donortrack.enums.BloodType;

@Data
public class BloodTypeChangeDto {
    @NotBlank
    private BloodType bloodType;
}
