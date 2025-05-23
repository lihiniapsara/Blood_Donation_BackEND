package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.entity.Camp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CampRepository extends JpaRepository<Camp, String> {
    boolean existsByEmail(String email);

}
