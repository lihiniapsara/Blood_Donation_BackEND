package org.example.blood_donation_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "hospital")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = {"bloodRequests", "camps"}) // Exclude these to prevent recursion
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID hospitalid;
    //@Column(unique = true,name = "hospital_name")
    @Column(name = "hospital_name")
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
    @ElementCollection
    private List<String> availableBloodGroups;
    private String bloodBankLicenseNumber;
    @ElementCollection
    private List<String> healthMinistryApprovalCertificate; // Image stored as byte array
    private boolean emergencyBloodServiceAvailable;
    private boolean bloodDonationCampFacility;
    private int numberOfBloodStorageUnits;

    @ManyToOne
    private User user;

    // Changed from @ManyToOne to @OneToMany
    @OneToMany(mappedBy = "hospital")
    @JsonIgnore
    private List<Blood_Request> bloodRequests;
    @OneToMany(mappedBy = "hospital")
    @JsonIgnore
    private List<Camp> camps;

    public Hospital(User byUsername, String hospitalName, String typeOfHospital, String registrationNumber, int yearOfEstablishment, String address, String city, String district, String province, String zipCode, String officialEmail, String contactNumber, String emergencyContactNumber, String website, boolean hasBloodBank, String bloodBankContactPersonName, String bloodBankContactNumber, List<String> availableBloodGroups, String bloodBankLicenseNumber, List<String> healthMinistryApprovalCertificate, boolean emergencyBloodServiceAvailable, boolean bloodDonationCampFacility, int numberOfBloodStorageUnits) {
        this.user = byUsername;
        this.hospitalName = hospitalName;
        this.typeOfHospital = typeOfHospital;
        this.registrationNumber = registrationNumber;
        this.yearOfEstablishment = yearOfEstablishment;
        this.address = address;
        this.city = city;
        this.district = district;
        this.province = province;
        this.zipCode = zipCode;
        this.officialEmail = officialEmail;
        this.contactNumber = contactNumber;
        this.emergencyContactNumber = emergencyContactNumber;
        this.website = website;
        this.hasBloodBank = hasBloodBank;
        this.bloodBankContactPersonName = bloodBankContactPersonName;
        this.bloodBankContactNumber = bloodBankContactNumber;
        this.availableBloodGroups = availableBloodGroups;
        this.bloodBankLicenseNumber = bloodBankLicenseNumber;
        this.healthMinistryApprovalCertificate = healthMinistryApprovalCertificate;
        this.emergencyBloodServiceAvailable = emergencyBloodServiceAvailable;
        this.bloodDonationCampFacility = bloodDonationCampFacility;
        this.numberOfBloodStorageUnits = numberOfBloodStorageUnits;
    }
}
