package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}