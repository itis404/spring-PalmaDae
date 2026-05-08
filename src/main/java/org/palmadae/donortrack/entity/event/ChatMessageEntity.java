package org.palmadae.donortrack.entity.event;

import jakarta.persistence.*;
import lombok.*;
import org.palmadae.donortrack.entity.UserEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@Table(name = "chat_messages")
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private EventChatEntity chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity sender;

    @Column(name = "message", nullable = false, length = 1000)
    private String message;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @PrePersist
    protected void onCreate() {
        sentAt = LocalDateTime.now();
    }
}