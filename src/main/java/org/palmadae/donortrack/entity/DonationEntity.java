package org.palmadae.donortrack.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.palmadae.donortrack.entity.enums.DonationType;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "donations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date_of_donation", "donation_type"}))
public class DonationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "date_of_donation", nullable = false)
    private LocalDate date;

    @Column(name = "donation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DonationType donationType;

    @Column(name = "cetificate")
    private String certificate;

    @Column(name = "status")
    private
}
