package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SettingRepository extends JpaRepository<Setting, UUID> {
}