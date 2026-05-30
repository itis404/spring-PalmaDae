package org.palmadae.donortrack.util;

import org.palmadae.donortrack.dto.DonationDto;
import org.palmadae.donortrack.dto.event.EventDto;
import org.palmadae.donortrack.entity.DonationEntity;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.entity.event.EventEntity;
import org.palmadae.donortrack.enums.DonationStatus;
import org.palmadae.donortrack.enums.EventStatus;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
    public DonationEntity donationDtoToEntity(DonationDto donationDto, UserEntity user) {
        DonationEntity donationEntity = DonationEntity.builder()
                .date(donationDto.getDate())
                .donationStatus(DonationStatus.IN_PROGRESS)
                .donationType(donationDto.getDonationType())
                .user(user)
                .build();

        return donationEntity;
    }

    public DonationEntity donationDtoToEntity(DonationDto donationDto) {
        DonationEntity donationEntity = DonationEntity.builder()
                .date(donationDto.getDate())
                .donationStatus(DonationStatus.IN_PROGRESS)
                .donationType(donationDto.getDonationType())
                .build();

        return donationEntity;
    }

    public EventDto eventEntityToDto(EventEntity eventEntity) {
        if (eventEntity == null) {
            return null;
        }

        return EventDto.builder()
                .id(eventEntity.getId())
                .title(eventEntity.getTitle())
                .description(eventEntity.getDescription())
                .address(eventEntity.getAddress())
                .eventDate(eventEntity.getEventDate())
                .maxParticipants(eventEntity.getMaxParticipants())
                .build();
    }
}
