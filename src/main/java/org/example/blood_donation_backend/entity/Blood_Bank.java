package org.example.blood_donation_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "blood_bank")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Blood_Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID bloodbankid;
    @Column(name = "hospital_name")
    private String hospitalName;
    private String location;
    private String district;
    private String latitude;
    private String longitude;
    private String contactDetails;
    @ElementCollection
    private List<String> stocklevels;
    private int storageCapacity;
    private String lastStockUpdateDate;
    private String managementType;
}
