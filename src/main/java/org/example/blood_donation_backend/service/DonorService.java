package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.DonorDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;

public interface DonorService {
    int saveDonor(DonorDTO donorDTO);

    ResponseDTO getAllDonors();

    ResponseDTO updateDonor(DonorDTO donorDTO);
}
