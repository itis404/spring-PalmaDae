package org.palmadae.donortrack.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.palmadae.donortrack.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity findById(Long id) {
        return entityManager.find(UserEntity.class, id);
    }
}
