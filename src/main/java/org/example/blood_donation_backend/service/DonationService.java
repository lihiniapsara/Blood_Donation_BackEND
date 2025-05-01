package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.DonationRequestDTO;
import org.example.blood_donation_backend.dto.DonationResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DonationService {
    @Transactional
    DonationResponseDTO processDonation(DonationRequestDTO request);
    List<DonationResponseDTO> getAllDonations();
    String determineCardType(DonationRequestDTO request);
}
