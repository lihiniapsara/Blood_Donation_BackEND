package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, String> {
    boolean existsByOfficialEmail(String officialEmail);
}


