package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.dto.Blood_RequestDTO;
import org.example.blood_donation_backend.entity.Blood_Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface Blood_RequestRepository extends JpaRepository<Blood_Request, String> {

    List<Blood_Request> findAll();


    @Query(value = "SELECT br.id, br.full_name, br.email, br.phone_number, br.blood_type, " +
            "br.district, br.message, br.request_date, br.hospital_name " +
            "FROM blood_request br",
            nativeQuery = true)

    List<Blood_Request> findAllBloodRequestsWithHospitalNative();
}

   /* @Query("SELECT new org.example.blood_donation_backend.dto.Blood_RequestDTO(" +
            "CAST(br.id AS string), br.fullName, br.email, br.phoneNumber, br.bloodType, " +
            "br.district, br.message, br.requestDate, h.hospitalName) " +
            "FROM Blood_Request br " +
            "LEFT JOIN br.hospital h")
    List<Blood_RequestDTO> findAllBloodRequestsWithHospitalName();}*/