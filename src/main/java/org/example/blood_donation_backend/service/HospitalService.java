package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.HospitalDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;

public interface HospitalService {
    int saveHospital(HospitalDTO hospitalDTO);

    ResponseDTO getAllHospitals();

}
