package org.example.blood_donation_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CampDTO {
    private String campDate;
    private String campName;
    private String contactNumber;
    private String email;
    private String campLocation;
    private String city;
    private String district;
    private String province;
    private String zipCode;
    private String hospitalname;
}
