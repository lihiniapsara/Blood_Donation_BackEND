package org.example.blood_donation_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "systemuser")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private String role;


    public User(User byUsername, String typeOfHospital, String registrationNumber, int yearOfEstablishment, String address, String city, String district, String province, String zipCode, String officialEmail, String contactNumber, String emergencyContactNumber, String website, boolean hasBloodBank, String bloodBankContactPersonName, String bloodBankContactNumber, List<String> availableBloodGroups, String bloodBankLicenseNumber, List<String> healthMinistryApprovalCertificate, boolean emergencyBloodServiceAvailable, boolean bloodDonationCampFacility, int numberOfBloodStorageUnits, String userName) {
    }
}
