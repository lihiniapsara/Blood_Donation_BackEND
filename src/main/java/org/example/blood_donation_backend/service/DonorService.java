package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.DonorDTO;

public interface DonorService {
    int saveDonor(DonorDTO donorDTO);
}
