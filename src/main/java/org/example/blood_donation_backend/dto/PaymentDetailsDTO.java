package org.example.blood_donation_backend.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentDetailsDTO {
    private String bankName;
    private String cardHolderName;

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    private String cardLastFourDigits;
    private String cardType;
    private String payhereTransactionId;
    private String proofOfPaymentUrl;
    private String referenceNumber;

}