package org.palmadae.donortrack.repository.donation;

import org.palmadae.donortrack.entity.DonationEntity;
import org.palmadae.donortrack.enums.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DonationJpaRepository extends JpaRepository<DonationEntity, Long> {
    List<DonationEntity> findAllByDate(LocalDate date);
    List<DonationEntity> findAllByDonationStatus(DonationStatus donationStatus);
    Optional<DonationEntity> findById(Long id);
    List<DonationEntity> findAllByUser_Id(Long userId);
}
