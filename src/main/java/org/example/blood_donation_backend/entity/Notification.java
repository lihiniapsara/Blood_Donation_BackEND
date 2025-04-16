package org.example.blood_donation_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;

    @Column(name = "blood_group", nullable = false)
    private String bloodGroup;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String template;

    @Column(nullable = false)
    private String status;

    @Column(name = "recipient_count", nullable = false)
    private int recipientCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}