package org.palmadae.donortrack.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.boot.internal.Abstract;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.entity.UserRole;
import org.palmadae.donortrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean isUserExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public boolean createUser(UserEntity user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return false;
        }

        user.setRole(UserRole.USER);
        userRepository.saveUser(user);

        return true;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}