package org.palmadae.donortrack.repository.email;

import org.palmadae.donortrack.entity.DonationEntity;
import org.palmadae.donortrack.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationJpaRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByEmail(String email);
    Optional<EmailVerification> findByEmailAndCode(String email, String code);
}
