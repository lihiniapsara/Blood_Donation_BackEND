package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.dto.DonationRequestDTO;
import org.example.blood_donation_backend.dto.DonationResponseDTO;
import org.example.blood_donation_backend.dto.PaymentDetailsDTO;
import org.example.blood_donation_backend.entity.Donation;
import org.example.blood_donation_backend.entity.PaymentDetails;
import org.example.blood_donation_backend.entity.User;
import org.example.blood_donation_backend.repo.DonationRepository;
import org.example.blood_donation_backend.repo.UserRepository;
import org.example.blood_donation_backend.service.DonationService;
import org.example.blood_donation_backend.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class DonationServiceImpl implements DonationService {
    private static final Logger logger = LoggerFactory.getLogger(DonationServiceImpl.class);

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DonationResponseDTO> getAllDonations() {
        List<Donation> donations = donationRepository.findAll();
        return donations.stream()
                .map(donation -> modelMapper.map(donation, DonationResponseDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public DonationResponseDTO processDonation(DonationRequestDTO request) {
        logger.info("Processing donation for email: {}", request.getEmail());

       /* // Validate input
        if (request.getAmount() <= 0) {
            logger.error("Invalid donation amount: {}", request.getAmount());
            throw new IllegalArgumentException("Donation amount must be greater than zero");
        }*/
        if (!Arrays.asList("CREDIT_CARD", "DEBIT_CARD", "BANK_TRANSFER", "PAYHERE").contains(request.getPaymentMethod())) {
            throw new IllegalArgumentException("Invalid payment method");
        }

        // Get or create user
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) {
            logger.info("Creating new user for email: {}", request.getEmail());
            User newUser = new User();
            newUser.setFullName(request.getFullName());
            newUser.setEmail(request.getEmail());
            newUser.setMobileNumber(request.getMobileNumber());
            newUser.setNicNumber(request.getNicNumber());
            newUser.setUsername(request.getEmail());
            user = userRepository.save(newUser);
        }

        // Process payment
        String paymentStatus;
        try {
            paymentStatus = paymentService.processPayment(request);
        } catch (Exception e) {
            logger.error("Payment processing failed for email: {}", request.getEmail(), e);
            throw new RuntimeException("Failed to process payment: " + e.getMessage(), e);
        }

        // Create payment details
        PaymentDetails paymentDetails = createPaymentDetails(request);

        // Create donation
        Donation donation = new Donation();
        donation.setUser(user);
        donation.setAmount(request.getAmount());
        String transactionId;
        do {
            transactionId = "BAI" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (donationRepository.existsByTransactionId(transactionId)); // Use existsByTransactionId
        donation.setTransactionId(transactionId);
        donation.setPaymentMethod(request.getPaymentMethod());
        donation.setPaymentStatus(paymentStatus);
        donation.setPaymentDetails(paymentDetails);
        donation.setDonationDate(LocalDateTime.now());

        donation = donationRepository.save(donation);
        logger.info("Donation saved with transactionId: {}", transactionId);

        // Create response
        return DonationResponseDTO.builder()
                .transactionId(donation.getTransactionId())
                .amount(donation.getAmount())
                .paymentMethod(donation.getPaymentMethod())
                .paymentStatus(donation.getPaymentStatus())
                .donationDate(donation.getDonationDate())
                .message("Thank you for your donation!")
                .build();
    }

    private PaymentDetails createPaymentDetails(DonationRequestDTO request) {
        PaymentDetails paymentDetails = new PaymentDetails();

        // Check if paymentDetails is not null
        PaymentDetailsDTO paymentDetailsDTO = request.getPaymentDetails();
        if (paymentDetailsDTO == null) {
            logger.error("Payment details are missing");
            throw new IllegalArgumentException("Payment details are required");
        }

        // Set fields
        paymentDetails.setBankName(paymentDetailsDTO.getBankName());
        paymentDetails.setCardHolderName(paymentDetailsDTO.getCardHolderName());

        // Check if cardNumber is not null before accessing length()
        String cardNumber = paymentDetailsDTO.getCardNumber();
        if (cardNumber != null && !cardNumber.isEmpty()) {
            String lastFourDigits = cardNumber.length() >= 4 ?
                    cardNumber.substring(cardNumber.length() - 4) : cardNumber;
            paymentDetails.setCardLastFourDigits(lastFourDigits);
        } else {
            paymentDetails.setCardLastFourDigits("N/A");
        }

        paymentDetails.setCardType(paymentDetailsDTO.getCardType());
        paymentDetails.setPayhereTransactionId(paymentDetailsDTO.getPayhereTransactionId());
        paymentDetails.setProofOfPaymentUrl(paymentDetailsDTO.getProofOfPaymentUrl());
        paymentDetails.setReferenceNumber(paymentDetailsDTO.getReferenceNumber());

        return paymentDetails;
    }

    @Override
    public String determineCardType(DonationRequestDTO request) {
        PaymentDetailsDTO paymentDetailsDTO = request.getPaymentDetails();
        if (paymentDetailsDTO == null) {
            logger.warn("Payment details are missing for card type determination");
            return "Unknown";
        }

        String cardNumber = paymentDetailsDTO.getCardNumber();
        if (cardNumber == null || cardNumber.isEmpty()) {
            logger.warn("Card number is missing for card type determination");
            return "Unknown";
        }

        cardNumber = cardNumber.replaceAll("\\s|-", "");
        char firstDigit = cardNumber.charAt(0);

        switch (firstDigit) {
            case '4':
                return "VISA";
            case '5':
                return "MASTERCARD";
            case '3':
                return "AMEX";
            default:
                return "OTHER";
        }
    }
}