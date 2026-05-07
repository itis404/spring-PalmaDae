package org.palmadae.donortrack.entity;

import jakarta.persistence.*;
import lombok.*;
import org.palmadae.donortrack.entity.enums.AuthProvider;
import org.palmadae.donortrack.entity.enums.BloodType;
import org.palmadae.donortrack.entity.enums.UserRole;
import org.palmadae.donortrack.entity.event.ChatMessage;
import org.palmadae.donortrack.entity.event.EventEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "hash_pass")
    private String hashPass;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.ALL)
    @Builder.Default
    private List<EventEntity> organizedEvents = new ArrayList<>();

    @ManyToMany(mappedBy = "participants")
    @Builder.Default
    private List<EventEntity> participatedEvents = new ArrayList<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ChatMessage> messages = new ArrayList<>();

    @Column(unique = true)
    private String yandexId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider;

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public boolean isOrganizerOf(EventEntity event) {
        return organizedEvents.contains(event);
    }

    public boolean isParticipantOf(EventEntity event) {
        return participatedEvents.contains(event);
    }
}