package org.example.blood_donation_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;
@Entity
@Table(name = "camp")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Camp {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
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

    @ManyToOne
    @JoinColumn(name = "hospitalid", nullable = false, columnDefinition = "BINARY(16)")
    private Hospital hospital;


    public Camp(Hospital byHospitalName, String campDate, String campName, String contactNumber, String email, String campLocation, String city, String district, String province, String zipCode) {
        this.campDate = campDate;
        this.campName = campName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.campLocation = campLocation;
        this.city = city;
        this.district = district;
        this.province = province;
        this.zipCode = zipCode;
        this.hospital = byHospitalName;
    }
}

