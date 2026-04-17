package org.palmadae.donortrack.repository.donation;


import jakarta.persistence.EntityManager;
import org.palmadae.donortrack.entity.DonationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DonationJpqlRepository {
    @Autowired
    private EntityManager em;
}
