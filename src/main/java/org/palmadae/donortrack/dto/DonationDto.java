package org.palmadae.donortrack.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.palmadae.donortrack.entity.enums.DonationType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Getter
@Setter
public class DonationDto {
    @NotBlank
    private LocalDate date;

    @NotBlank
    private DonationType donationType;

    private String certificate;
}
