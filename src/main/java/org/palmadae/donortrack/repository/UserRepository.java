package org.palmadae.donortrack.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.palmadae.donortrack.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity findById(Long id) {
        return entityManager.find(UserEntity.class, id);
    }

    public Optional<UserEntity> findByUsername(String username) {
        try {
            UserEntity user = entityManager.createQuery(
                            "SELECT u FROM UserEntity u WHERE u.username = :username",
                            UserEntity.class
                    )
                    .setParameter("username", username)
                    .getSingleResult();

            return Optional.of(user);

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }



    @Transactional
    public void saveUser(@NonNull UserEntity user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } {
            entityManager.merge(user);
        }
    }
}
