package org.palmadae.donortrack.dto.event;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;

    @NotBlank(message = "Укажите название мероприятия")
    private String title;

    @NotBlank(message = "Укажите описание мероприятия")
    private String description;

    @NotNull(message = "Укажите дату мероприятия")
    @Future(message = "Дата должна быть в будущем")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime eventDate;

    @NotBlank(message = "Укажите адрес мероприятия")
    private String address;

    @NotNull(message = "Укажите количество участников")
    @Min(value = 1, message = "Минимальное количество участников 1")
    private Integer maxParticipants;
}