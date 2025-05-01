package org.example.blood_donation_backend.controller;

import org.example.blood_donation_backend.dto.DonationRequestDTO;
import org.example.blood_donation_backend.dto.DonationResponseDTO;
import org.example.blood_donation_backend.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/donations")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @PostMapping("/donate")
    public ResponseEntity<DonationResponseDTO> makeDonation(@RequestBody DonationRequestDTO request) {
        DonationResponseDTO response = donationService.processDonation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<String> checkDonationStatus(@PathVariable String transactionId) {
        // In a real application, fetch and return the donation status
        return ResponseEntity.ok("SUCCESS");
    }

    @GetMapping
    public ResponseEntity<List<DonationResponseDTO>> getAllDonations() {
        List<DonationResponseDTO> donations = donationService.getAllDonations();
        return ResponseEntity.ok(donations);
    }
}
