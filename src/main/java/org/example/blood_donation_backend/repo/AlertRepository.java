package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}
