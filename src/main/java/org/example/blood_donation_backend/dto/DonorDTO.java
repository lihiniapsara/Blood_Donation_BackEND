package org.example.blood_donation_backend.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class DonorDTO {
    private String fullName;
    private String dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String nicOrPassport;
    private String contact;
    private String email;
    private String address;
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


    public DonorDTO(String nicOrPassport, String email) {
        this.nicOrPassport = nicOrPassport;
        this.email = email;
    }
}
