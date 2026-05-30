package org.palmadae.donortrack.repository.event;

import org.palmadae.donortrack.entity.event.EventChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EventChatRepository extends JpaRepository<EventChatEntity, Long> {
    Optional<EventChatEntity> findByEventId(Long eventId);
    boolean existsByEventId(Long eventId);

    @Modifying
    @Query("UPDATE EventChatEntity c SET c.isActive = false WHERE c.event.id = :eventId")
    void deactivateChatByEventId(Long eventId);

    @Modifying
    @Transactional
    @Query("UPDATE EventChatEntity c SET c.isActive = true WHERE c.event.id = :eventId")
    void activateChatByEventId(@Param("eventId") Long eventId);
}