package org.example.blood_donation_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "settings")
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "notification_threshold", nullable = false)
    private int notificationThreshold;

    @Column(name = "notification_frequency", nullable = false)
    private String notificationFrequency;
}