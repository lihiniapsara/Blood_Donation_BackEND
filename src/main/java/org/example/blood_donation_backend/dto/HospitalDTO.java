package org.example.blood_donation_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HospitalDTO {
    private String hospitalName;
    private String typeOfHospital;
    private String registrationNumber;
    private int yearOfEstablishment;
    private String address;
    private String city;
    private String district;
    private String province;
    private String zipCode;
    private String officialEmail;
    private String contactNumber;
    private String emergencyContactNumber;
    private String website;
    private boolean hasBloodBank;
    private String bloodBankContactPersonName;
    private String bloodBankContactNumber;
    private List<String> availableBloodGroups;
    private String bloodBankLicenseNumber;
    private List<String> healthMinistryApprovalCertificate; // Image stored as byte array
    private boolean emergencyBloodServiceAvailable;
    private boolean bloodDonationCampFacility;
    private int numberOfBloodStorageUnits;
}
