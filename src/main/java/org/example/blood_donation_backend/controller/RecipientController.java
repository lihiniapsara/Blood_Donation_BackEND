package org.example.blood_donation_backend.controller;


import lombok.RequiredArgsConstructor;
import org.example.blood_donation_backend.dto.BloodCountDTO;
import org.example.blood_donation_backend.service.Blood_BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipients")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class RecipientController {
    @Autowired
    private Blood_BankService bloodBankService;

    @GetMapping("/summary")
    public ResponseEntity<List<BloodCountDTO>> getRecipientSummary(
            @RequestParam String hospitalName,
            @RequestParam List<String> bloodTypes) {
        if (hospitalName.isEmpty() || bloodTypes.isEmpty()) {
            return ResponseEntity.badRequest().body(List.of());
        }
        return ResponseEntity.ok(bloodBankService.getBloodCounts(hospitalName, bloodTypes));
    }
}
