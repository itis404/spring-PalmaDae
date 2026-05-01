package org.palmadae.donortrack.repository.chat;

import org.palmadae.donortrack.entity.event.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChatMessageJpaRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatIdOrderBySentAtAsc(Long chatId);
    List<ChatMessage> findByChatIdOrderBySentAtDesc(Long chatId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM ChatMessage m WHERE m.chat.id = :chatId")
    void deleteAllByChatId(@Param("chatId") Long chatId);
}