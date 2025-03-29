package org.example.blood_donation_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Blood_InventoryDTO {
    private String bloodType;
    private Date donationDate;
    private Date expiryDate;
    private int units;
}
