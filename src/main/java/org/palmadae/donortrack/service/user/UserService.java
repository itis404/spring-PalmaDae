package org.palmadae.donortrack.service.user;

import jakarta.transaction.Transactional;
import org.palmadae.donortrack.dto.UserDto;
import org.palmadae.donortrack.dto.profile.EmailChangeDto;
import org.palmadae.donortrack.dto.profile.PasswordChangeDto;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.entity.enums.BloodType;
import org.palmadae.donortrack.entity.enums.UserRole;
import org.palmadae.donortrack.repository.user.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserJpaRepository jpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    public Optional<UserEntity> findByUsername(String username) { return jpaRepository.findByUsername(username); }

    @Transactional
    public boolean createUser(UserDto dto) {
        if (jpaRepository.existsByUsername(dto.getUsername())) {
            return false;
        }

        if (jpaRepository.existsByEmail(dto.getUsername())) {
            return false;
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        UserEntity user = UserEntity.builder()
                        .username(dto.getUsername())
                        .email(dto.getEmail())
                        .hash_pass(encodedPassword)
                        .role(UserRole.USER)
                                .build();


        jpaRepository.save(user);
        return true;
    }


    @Transactional
    public void changeEmail(String username, EmailChangeDto dto) {
        UserEntity user = jpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getEmail().equals(dto.getNewEmail()) && jpaRepository.existsByEmail(dto.getNewEmail())) {
            throw new RuntimeException("Email already in use");
        }
        user.setEmail(dto.getNewEmail());
        jpaRepository.save(user);
    }

    @Transactional
    public void changePassword(String username, PasswordChangeDto dto) {
        UserEntity user = jpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getHash_pass())) {
            throw new RuntimeException("Old password is incorrect");
        }
        user.setHash_pass(passwordEncoder.encode(dto.getNewPassword()));
        jpaRepository.save(user);
    }

    @Transactional
    public void changeBloodType(String username, BloodType bloodType) {
        UserEntity user = jpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setBloodType(bloodType);
        jpaRepository.save(user);
    }
}