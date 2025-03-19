package org.example.blood_donation_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Entity
@Table(name = "camp")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Camp {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID campid;
    private String campDate;
    private String campName;
    private String contactNumber;
    private String email;
    private String campLocation;
    private String city;
    private String district;
    private String province;
    private String zipCode;
}
