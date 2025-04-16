package org.example.blood_donation_backend.dto;

import lombok.Data;

@Data
public class SettingRequestDTO {
    private int notificationThreshold;
    private String notificationFrequency;
}
