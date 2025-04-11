package org.example.blood_donation_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "blood_request")
public class Blood_Request {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String bloodType;
    private String district;
    private String message;

    @Column(name = "request_date", columnDefinition = "DATE")
    private LocalDate requestDate;

    @ManyToOne
    @JoinColumn(name = "hospital_name", nullable = false)
    private Hospital hospital;

    public Blood_Request(Hospital hospital, String fullName, String email, String phoneNumber, String bloodType, String district, String message) {
        this.hospital = hospital;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.bloodType = bloodType;
        this.district = district;
        this.message = message;
    }
}