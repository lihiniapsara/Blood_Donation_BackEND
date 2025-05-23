package org.example.blood_donation_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
@Entity
@Table(name = "donor")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Donor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fullName;
    private String dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String nicOrPassport;
    private String contact;
    private String email;
    private String Address;
    private String city;
    private String district;
    private String province;
    private String zipCode;
    private boolean hasDiabetes;
    private boolean hasHeartProblem;
    private boolean hasLowPressure;
    private boolean hasHighPressure;
    private boolean hasSocialIssues;
    private boolean hasTattoos;
    private boolean hasOtherIssues;
    private Date donationDate;


    public Donor(String fullName, String dateOfBirth, String gender, String bloodGroup,
                 String nicOrPassport, String contact, String email, String address,
                 String city, String district, String province, String zipCode) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.nicOrPassport = nicOrPassport;
        this.contact = contact;
        this.email = email;
        this.Address = address;
        this.city = city;
        this.district = district;
        this.province = province;
        this.zipCode = zipCode;
    }
}
