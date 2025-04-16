package org.example.blood_donation_backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.blood_donation_backend.dto.AlertRequestDTO;
import org.example.blood_donation_backend.entity.Alert;
import org.example.blood_donation_backend.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AlertController {
    private final AlertService alertService;

    @PostMapping("/alerts")
    public ResponseEntity<Alert> createAlert(@Valid @RequestBody AlertRequestDTO request) {
        return ResponseEntity.ok(alertService.createAlert(request));
    }

    @GetMapping("/alerts/getAll")
    public ResponseEntity<List<Alert>> getAllAlerts() {
        return ResponseEntity.ok(alertService.getAllAlerts());
    }

    @GetMapping("/alerts/{id}")
    public ResponseEntity<Alert> getAlertById(@PathVariable Long id) {
        return ResponseEntity.ok(alertService.getAlertById(id));
    }
}