package org.palmadae.donortrack.dto.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.palmadae.donortrack.entity.enums.BloodType;
import org.springframework.stereotype.Component;

@Data
public class BloodTypeChangeDto {
    @NotBlank
    private BloodType bloodType;
}
