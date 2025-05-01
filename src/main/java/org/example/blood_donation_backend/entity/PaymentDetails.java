package org.example.blood_donation_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payment_details")
@Data
public class PaymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardHolderName;
    private String cardLastFourDigits;
    private String cardType;
    private String bankName;
    private String referenceNumber;
    private String proofOfPaymentUrl;
    private String payhereTransactionId;
}