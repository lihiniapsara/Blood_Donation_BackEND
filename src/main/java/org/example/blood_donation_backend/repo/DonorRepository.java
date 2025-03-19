package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.dto.DonorDTO;
import org.example.blood_donation_backend.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, String> {
    boolean existsByEmail(String email);

}
