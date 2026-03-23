package org.palmadae.donortrack.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager;

    public boolean createUser(UserEntity user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername());

        if (userEntity == null) {
            return false;
        }

        user.setRole(UserRole.USER);
        userRepository.saveUser(user);
        return true;
    }


}
