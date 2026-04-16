package org.palmadae.donortrack.service;

import jakarta.transaction.Transactional;
import org.palmadae.donortrack.dto.UserDto;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.entity.UserRole;
import org.palmadae.donortrack.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}