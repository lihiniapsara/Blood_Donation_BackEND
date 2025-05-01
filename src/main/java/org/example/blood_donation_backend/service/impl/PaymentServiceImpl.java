package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.dto.DonationRequestDTO;
import org.example.blood_donation_backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    @Value("${file.upload.directory:uploads}")
    private String uploadDirectory;
    @Override
    public String processPayment(DonationRequestDTO request) {
        // In a real application, this would integrate with a payment gateway
        // For now, we'll simulate a successful payment

        try {
            // Simulate payment processing delay
            Thread.sleep(1000);

            // For demo purposes, always return success
            // In production, you'd integrate with real payment gateways
            return "SUCCESS";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "FAILED";
        }
    }
@Override
    public String saveProofOfPayment(String base64File, String fileName) {
        try {
            // Create uploads directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            Path filePath = uploadPath.resolve(uniqueFileName);

            // Decode and save the file
            byte[] fileBytes = Base64.getDecoder().decode(base64File.split(",")[1]);
            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                fos.write(fileBytes);
            }

            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save proof of payment", e);
        }
    }
}
