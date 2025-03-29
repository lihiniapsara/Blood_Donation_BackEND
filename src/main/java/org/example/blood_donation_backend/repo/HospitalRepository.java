package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, String> {
/*
    boolean findByHospitalid(String hospitalid); // Ensure this matches the entity field
*/

    boolean existsByOfficialEmail(String officialEmail);

    Hospital findByHospitalName(String hospitalName);
}


