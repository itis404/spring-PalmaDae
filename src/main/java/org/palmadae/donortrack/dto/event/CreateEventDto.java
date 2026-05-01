package org.palmadae.donortrack.dto.event;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEventDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    @FutureOrPresent
    private LocalDateTime eventDate;

    @NotBlank
    private String address;

    @NotNull
    @Min(1)
    private Integer maxParticipants;
}