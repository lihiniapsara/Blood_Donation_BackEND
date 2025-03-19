package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.HospitalDTO;

public interface HospitalService {
    int saveHospital(HospitalDTO hospitalDTO);
}
