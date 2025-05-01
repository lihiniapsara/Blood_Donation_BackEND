package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.DonationRequestDTO;

public interface PaymentService {

    String processPayment(DonationRequestDTO request);

    String saveProofOfPayment(String base64File, String fileName);
}
