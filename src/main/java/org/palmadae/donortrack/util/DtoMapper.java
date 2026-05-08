package org.palmadae.donortrack.util;

import org.palmadae.donortrack.dto.DonationDto;
import org.palmadae.donortrack.dto.event.EventDto;
import org.palmadae.donortrack.entity.DonationEntity;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.entity.event.EventEntity;
import org.palmadae.donortrack.enums.DonationStatus;

public class DtoMapper {
    public EventEntity eventDtoToEntity(EventDto eventDto) {

    }

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
}
