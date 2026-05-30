package org.palmadae.donortrack.repository.event;

import org.palmadae.donortrack.entity.event.EventEntity;
import org.palmadae.donortrack.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByStatus(EventStatus status);
    List<EventEntity> findByOrganizerId(Long organizerId);
    List<EventEntity> findByStatusAndEventDateAfterOrderByEventDateAsc(EventStatus status, LocalDateTime now);
    List<EventEntity> findByStatusAndTitleContainingIgnoreCase(EventStatus status, String title);
}