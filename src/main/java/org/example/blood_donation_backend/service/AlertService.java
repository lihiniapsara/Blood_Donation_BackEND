package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.AlertRequestDTO;
import org.example.blood_donation_backend.entity.Alert;

import java.util.List;

public interface AlertService {
    Alert createAlert(AlertRequestDTO request);

    List<Alert> getAllAlerts();

    Alert getAlertById(Long id);
}
