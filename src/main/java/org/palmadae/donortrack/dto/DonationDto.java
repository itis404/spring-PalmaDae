package org.palmadae.donortrack.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.palmadae.donortrack.enums.DonationType;

import java.time.LocalDate;

@Data
public class DonationDto {
    @PastOrPresent(message = "Дата не может быть в будущем")
    @NotNull(message = "Укажите дату донации")
    private LocalDate date;

    @NotNull(message = "Укажите тип донации")
    private DonationType donationType;

    private String certificate;
}