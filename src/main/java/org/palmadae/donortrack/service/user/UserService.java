package org.palmadae.donortrack.service.user;

import jakarta.transaction.Transactional;
import org.palmadae.donortrack.dto.UserDto;
import org.palmadae.donortrack.dto.YandexUserDto;
import org.palmadae.donortrack.dto.profile.EmailChangeDto;
import org.palmadae.donortrack.dto.profile.PasswordChangeDto;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.entity.enums.AuthProvider;
import org.palmadae.donortrack.entity.enums.BloodType;
import org.palmadae.donortrack.entity.enums.UserRole;
import org.palmadae.donortrack.exception.custom.email.EmailAlreadyExistsException;
import org.palmadae.donortrack.exception.custom.user.InvalidOldPasswordException;
import org.palmadae.donortrack.exception.custom.user.UserNotFoundException;
import org.palmadae.donortrack.repository.user.UserJpaRepository;
import org.palmadae.donortrack.repository.user.UserJpqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserJpaRepository jpaRepository;

    @Autowired
    private UserJpqlRepository userJpqlRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    public Optional<UserEntity> findByUsername(String username) { return jpaRepository.findByUsername(username); }

    public Optional<UserEntity> findByEmail(String email) { return jpaRepository.findByEmail(email); }

    public Optional<UserEntity> findByYandexId(String id) { return jpaRepository.findByYandexId(id); }

    @Transactional
    public boolean createUserLocal(UserDto dto) {

        if (jpaRepository.existsByUsername(dto.getUsername())) {
            return false;
        }

        if (jpaRepository.existsByEmail(dto.getEmail())) {
            return false;
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        UserEntity user = UserEntity.builder()
                .username(dto.getUsername().toLowerCase())
                .email(dto.getEmail())
                .hashPass(encodedPassword)
                .role(UserRole.USER)
                .provider(AuthProvider.LOCAL)
                .build();

        jpaRepository.save(user);
        return true;
    }

    @Transactional
    public UserEntity createUserYandex(YandexUserDto dto) {

        return jpaRepository.findByYandexId(dto.getId())
                .orElseGet(() -> {
                    UserEntity user = UserEntity.builder()
                            .username(dto.getLogin())
                            .email(dto.getDefaultEmail())
                            .yandexId(dto.getId())
                            .provider(AuthProvider.YANDEX)
                            .role(UserRole.USER)
                            .hashPass("")
                            .build();

                    return jpaRepository.save(user);
                });
    }

    @Transactional
    public void changeEmail(String username, EmailChangeDto dto) {
        UserEntity user = jpaRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        String newEmail = dto.getNewEmail();

        if (!user.getEmail().equals(newEmail)
                && jpaRepository.existsByEmail(newEmail)) {
            throw new EmailAlreadyExistsException(newEmail);
        }

        user.setEmail(newEmail);
    }

    @Transactional
    public void changePassword(String username, PasswordChangeDto dto) {
        UserEntity user = jpaRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getHashPass())) {
            throw new InvalidOldPasswordException();
        }
        user.setHashPass(passwordEncoder.encode(dto.getNewPassword()));
        jpaRepository.save(user);
    }

    @Transactional
    public void changeBloodType(String username, BloodType bloodType) {
        UserEntity user = jpaRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        int updated = userJpqlRepository.updateBloodType(user.getId(), bloodType);

        if (updated == 0) {
            throw new UserNotFoundException(username);
        }
    }

    @Transactional
    public void changeCity(String username, String city) {
        UserEntity user = jpaRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        user.setCity(city);
    }

    public List<UserEntity> getUsersByCity(String city) {
        return userJpqlRepository.findByCity(city);
    }
}