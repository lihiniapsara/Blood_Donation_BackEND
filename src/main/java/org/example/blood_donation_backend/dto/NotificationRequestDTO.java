package org.example.blood_donation_backend.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class NotificationRequestDTO {
    private String hospitalName;
    private String bloodGroup;
    private String template;
}
