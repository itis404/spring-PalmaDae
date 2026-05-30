package org.palmadae.donortrack.dto.profile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.palmadae.donortrack.entity.DonationEntity;

import java.util.List;

@Getter
@Setter
@Builder
public class ProfileDto {
    private String username;
    private String email;
    private String bloodType;
    private String city;
    private List<DonationEntity> donations;
}