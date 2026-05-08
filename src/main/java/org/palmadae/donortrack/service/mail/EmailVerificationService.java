package org.palmadae.donortrack.service.mail;

import org.palmadae.donortrack.entity.EmailVerificationEntity;
import org.palmadae.donortrack.exception.custom.email.EmailCodeExpiredException;
import org.palmadae.donortrack.exception.custom.email.InvalidEmailCodeException;
import org.palmadae.donortrack.repository.email.EmailVerificationJpaRepository;
import org.palmadae.donortrack.util.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailVerificationService {

    @Autowired
    private EmailVerificationJpaRepository repository;

    @Autowired
    private MailService mailService;

    public void sendCode(String email) {

        String code = CodeGenerator.generateVerificationCode();

        EmailVerificationEntity verification = new EmailVerificationEntity();
        verification.setEmail(email);
        verification.setCode(code);
        verification.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        verification.setVerified(false);

        repository.save(verification);

        mailService.sendVerificationCode(email, code);
    }

    public boolean verifyCode(String email, String code) {

        EmailVerificationEntity v = repository.findByEmailAndCode(email, code)
                .orElseThrow(InvalidEmailCodeException::new);

        if (v.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new EmailCodeExpiredException();
        }

        v.setVerified(true);
        repository.save(v);

        return true;
    }
}