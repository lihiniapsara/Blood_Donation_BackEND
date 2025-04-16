package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.HospitalDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;

import java.util.List;

public interface HospitalService {
    int saveHospital(HospitalDTO hospitalDTO);

    ResponseDTO getAllHospitals();

    List<HospitalDTO> getHospitalsByDistrict(String district);
}
