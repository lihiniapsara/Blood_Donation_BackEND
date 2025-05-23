package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.DonorDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.entity.Donor;

import java.util.List;

public interface DonorService {
    int saveDonor(DonorDTO donorDTO);

    ResponseDTO getAllDonors();

    ResponseDTO updateDonor(DonorDTO donorDTO);

/*
    List<DonorDTO> getDonorsByDistrict(String district);
*/

    List<Donor> findByDistrictAndBloodGroupIn(String district, List<String> bloodTypes);
}
