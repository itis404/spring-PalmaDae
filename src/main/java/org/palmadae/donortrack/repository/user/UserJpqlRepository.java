package org.palmadae.donortrack.repository.user;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.enums.BloodType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJpqlRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public int updateBloodType(Long userId, BloodType bloodType) {
        return em.createQuery("""
                update UserEntity u
                set u.bloodType = :bloodType
                where u.id = :userId
        """)
                .setParameter("bloodType", bloodType)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    public List<UserEntity> findByCity(String city) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);

        Root<UserEntity> user = cq.from(UserEntity.class);

        cq.select(user)
                .where(cb.equal(user.get("city"), city));

        return em.createQuery(cq).getResultList();
    }
}