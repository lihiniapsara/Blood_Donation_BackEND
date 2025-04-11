package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.Blood_RequestDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;

import java.util.List;

public interface Blood_RequestService {

    int save(Blood_RequestDTO bloodRequestDTO);
/*
    List<Blood_RequestDTO> getAllBloodRequest();
*/
}
