package org.palmadae.donortrack.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.palmadae.donortrack.entity.enums.BloodType;
import org.springframework.stereotype.Repository;

@Repository
public class UserJpqlRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void updateBloodType(Long userId, BloodType bloodType) {

    }
}
