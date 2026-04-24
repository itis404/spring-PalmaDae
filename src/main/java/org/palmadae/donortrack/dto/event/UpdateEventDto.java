package org.palmadae.donortrack.dto.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateEventDto {
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private String address;
    private Integer maxParticipants;
}