package org.example.blood_donation_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "donations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String transactionId;

    @Column(nullable = false)
    private String paymentMethod;

    @Column(nullable = false)
    private String paymentStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_details_id")
    private PaymentDetails paymentDetails;

    @Column(nullable = false)
    private LocalDateTime donationDate;

    @PrePersist
    protected void onCreate() {
        donationDate = LocalDateTime.now();
    }
}
