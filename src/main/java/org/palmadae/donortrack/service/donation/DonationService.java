package org.palmadae.donortrack.service.donation;

import org.palmadae.donortrack.entity.DonationEntity;
import org.palmadae.donortrack.repository.donation.DonationJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DonationService {
    @Autowired
    private DonationJpaRepository jpaRepository;

    public List<DonationEntity> getDonationsInDate(LocalDate date) {
        return jpaRepository.findAllByDate(date);
    }

    public boolean saveDonation(DonationEntity donationEntity) {
        jpaRepository.save(donationEntity);

        return true;
    }

}
