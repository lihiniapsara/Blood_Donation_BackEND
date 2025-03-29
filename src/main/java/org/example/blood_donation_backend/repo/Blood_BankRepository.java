package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.entity.Blood_Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Blood_BankRepository extends JpaRepository<Blood_Bank, String> {
    List<Blood_Bank> findAll();

    boolean existsByHospitalName(String hospitalName);

    Blood_Bank findByHospitalName(String hospitalName);

}
