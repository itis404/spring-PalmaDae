package org.palmadae.donortrack.service;

import org.palmadae.donortrack.dto.profile.ProfileDto;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.exception.custom.user.UserNotFoundException;
import org.palmadae.donortrack.service.donation.DonationService;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private DonationService donationService;

    @Autowired
    private UserService userService;

    public ProfileDto getProfile(String username) {

        UserEntity user = userService.findByUsername(username);

        return ProfileDto.builder()
                .username(user.getUsername())
                .bloodType(user.getBloodType() != null ? user.getBloodType().getDisplayName() : "не указана")
                .city(user.getCity() != null ? user.getCity() : "не указан")
                .donations(donationService.getUserDonations(user.getId()))
                .build();
    }
}
