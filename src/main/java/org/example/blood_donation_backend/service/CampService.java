package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.CampDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface CampService {
    int saveCamp(CampDTO campDTO);

    ResponseDTO getAll();

    ResponseEntity <ResponseDTO>updateCamp(CampDTO campDTO);
}
