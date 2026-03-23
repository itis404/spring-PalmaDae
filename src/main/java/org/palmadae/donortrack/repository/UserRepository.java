package org.palmadae.donortrack.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.palmadae.donortrack.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity findById(Long id) {
        return entityManager.find(UserEntity.class, id);
    }

    public UserEntity findByUsername(String username) {
        return entityManager.find(UserEntity.class, username);
    }

    @Transactional
    public void saveUser(UserEntity user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } {
            entityManager.merge(user);
        }
    }
}
