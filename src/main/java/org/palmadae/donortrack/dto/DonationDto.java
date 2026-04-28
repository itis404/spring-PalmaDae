package org.palmadae.donortrack.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.palmadae.donortrack.entity.enums.DonationType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
public class DonationDto {
    @NotNull
    private LocalDate date;
    @NotBlank
    private DonationType donationType;
    private String certificate;
}
