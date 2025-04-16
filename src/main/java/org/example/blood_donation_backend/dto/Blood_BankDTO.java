package org.example.blood_donation_backend.dto;

import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Blood_BankDTO {
    private String hospitalName;
    private String location;
    private String district;
    private String latitude;
    private String longitude;
    private String contactDetails;
    private List<String> stocklevels;
    private int storageCapacity;
    private String lastStockUpdateDate;
    private String managementType;


    public Blood_BankDTO(String hospitalName,List<String> stocklevels) {
        this.hospitalName = hospitalName;
        this.stocklevels = stocklevels;
    }
}
