package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.entity.Blood_Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Blood_BankRepository extends JpaRepository<Blood_Bank, String> {
    List<Blood_Bank> findAll();

    boolean existsByHospitalName(String hospitalName);

    //Blood_Bank findByHospitalName(String hospitalName);
    List<Blood_Bank> findByDistrict(String district);
    Optional<Blood_Bank> findByHospitalName(String hospitalName);}
