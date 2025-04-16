package org.example.blood_donation_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String district;
    private String hospital;
    @ElementCollection
    private List<String> bloodTypes;
    private String priority;
    private String emergencyType;
    private String subject;
    @Column(length = 1000)
    private String body;
    private String status;
}
