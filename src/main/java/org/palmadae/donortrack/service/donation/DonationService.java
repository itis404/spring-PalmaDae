package org.palmadae.donortrack.service.donation;

import org.palmadae.donortrack.entity.DonationEntity;
import org.palmadae.donortrack.entity.enums.DonationStatus;
import org.palmadae.donortrack.repository.donation.DonationJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class DonationService {
    @Autowired
    private DonationJpaRepository jpaRepository;

    public List<DonationEntity> getDonationsInDate(LocalDate date) {
        return jpaRepository.findAllByDate(date);
    }

    public boolean saveDonation(DonationEntity donationEntity, MultipartFile certificateFile) {

        if (certificateFile != null && !certificateFile.isEmpty()) {
            try {
                String uploadDir = "src/main/resources/images/certificates/";



                String fileName = LocalDate.now() + "-0" +  donationEntity.getUser().getId().toString() + donationEntity.getDonationType().toString() + ".jpg";

                java.nio.file.Path path = java.nio.file.Paths.get(uploadDir + fileName);
                java.nio.file.Files.createDirectories(path.getParent());
                java.nio.file.Files.write(path, certificateFile.getBytes());

                donationEntity.setCertificate(fileName);

            } catch (Exception e) {
                throw new RuntimeException("Certificate upload failed", e);
            }
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
