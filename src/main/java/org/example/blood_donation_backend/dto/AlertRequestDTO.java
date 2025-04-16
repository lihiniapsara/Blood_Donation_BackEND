package org.example.blood_donation_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertRequestDTO {
    private String district;
    private String hospital;
    private List<String> bloodTypes;
    private String priority;
    private String emergencyType;
    private String subject;
    private String body;
}
