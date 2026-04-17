package org.palmadae.donortrack.repository.donation;

import org.palmadae.donortrack.entity.DonationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DonationJpaRepository extends JpaRepository<DonationEntity, Long> {
    List<DonationEntity> findAllByDate(LocalDate date);

}
