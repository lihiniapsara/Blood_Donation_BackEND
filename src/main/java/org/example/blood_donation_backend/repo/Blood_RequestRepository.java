package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.dto.Blood_RequestDTO;
import org.example.blood_donation_backend.entity.Blood_Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface Blood_RequestRepository extends JpaRepository<Blood_Request, UUID> {

    List<Blood_Request> findAll();

    @Query("SELECT new org.example.blood_donation_backend.dto.Blood_RequestDTO(" +
            "CAST(br.id AS string), br.fullName, br.email, br.phoneNumber, br.bloodType, " +
            "br.district, br.message, br.requestDate, h.hospitalName) " +
            "FROM Blood_Request br " +
            "LEFT JOIN br.hospital h")
    List<Blood_RequestDTO> findAllBloodRequestsWithHospitalName();}