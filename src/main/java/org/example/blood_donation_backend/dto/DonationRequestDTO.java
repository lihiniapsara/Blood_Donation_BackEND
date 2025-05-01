package org.example.blood_donation_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class DonationRequestDTO {
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Donation date is required")
    private LocalDateTime donationDate;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    @NotBlank(message = "Payment status is required")
    private String paymentStatus;

    @NotBlank(message = "Transaction ID is required")
    private String transactionId;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String mobileNumber;
    private String nicNumber;

    private PaymentDetailsDTO paymentDetails;

}
