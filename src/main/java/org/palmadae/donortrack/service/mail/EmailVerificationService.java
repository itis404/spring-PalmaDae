package org.palmadae.donortrack.service.mail;

import org.palmadae.donortrack.entity.EmailVerification;
import org.palmadae.donortrack.repository.email.EmailJpaRepository;
import org.palmadae.donortrack.util.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailVerificationService {

    @Autowired
    private EmailJpaRepository repository;

    @Autowired
    private MailService mailService;

    public void sendCode(String email) {

        String code = CodeGenerator.generateVerificationCode();

        EmailVerification verification = new EmailVerification();
        verification.setEmail(email);
        verification.setCode(code);
        verification.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        verification.setVerified(false);

        repository.save(verification);

        mailService.sendVerificationCode(email, code);
    }

    public boolean verifyCode(String email, String code) {

        EmailVerification v = repository.findByEmailAndCode(email, code)
                .orElseThrow(() -> new RuntimeException("Invalid code"));

        if (v.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Code expired");
        }

        v.setVerified(true);
        repository.save(v);

        return true;
    }
}