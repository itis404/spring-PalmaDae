package org.palmadae.donortrack.service.donation;

import org.palmadae.donortrack.entity.DonationEntity;
import org.palmadae.donortrack.entity.enums.DonationStatus;
import org.palmadae.donortrack.repository.donation.DonationJpaRepository;
import org.palmadae.donortrack.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class DonationService {
    @Autowired
    private DonationJpaRepository jpaRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public List<DonationEntity> getDonationsInDate(LocalDate date) {
        return jpaRepository.findAllByDate(date);
    }

    public boolean saveDonation(DonationEntity donationEntity, MultipartFile certificateFile) {

        if (certificateFile != null && !certificateFile.isEmpty()) {
            String fileName = fileStorageService.upload(certificateFile);
            donationEntity.setCertificate(fileName);
        }

        jpaRepository.save(donationEntity);
        return true;
    }

    public List<DonationEntity> getByStatus(DonationStatus status) {
        return jpaRepository.findAllByDonationStatus(status);
    }

    public void updateStatus(Long id, DonationStatus status) {
        DonationEntity donation = jpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        donation.setDonationStatus(status);

        jpaRepository.save(donation);
    }

}
