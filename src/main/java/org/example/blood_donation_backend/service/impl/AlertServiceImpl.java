package org.example.blood_donation_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.blood_donation_backend.dto.AlertRequestDTO;
import org.example.blood_donation_backend.entity.Alert;
import org.example.blood_donation_backend.entity.Donor;
import org.example.blood_donation_backend.repo.AlertRepository;
import org.example.blood_donation_backend.repo.DonorRepository;
import org.example.blood_donation_backend.service.AlertService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final DonorRepository donorRepository;
    private final JavaMailSender mailSender; // For sending emails

    @Override
    public Alert createAlert(AlertRequestDTO request) {
        // Save the alert
        Alert alert = new Alert();
        alert.setDistrict(request.getDistrict());
        alert.setHospital(request.getHospital());
        alert.setBloodTypes(request.getBloodTypes());
        alert.setPriority(request.getPriority());
        alert.setEmergencyType(request.getEmergencyType());
        alert.setSubject(request.getSubject());
        alert.setBody(request.getBody());
        alert.setStatus("sent");
        alert.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")));
        Alert savedAlert = alertRepository.save(alert);

        // Replace placeholders in the email body
        String emailBody = request.getBody()
                .replace("[Hospital]", request.getHospital())
                .replace("[District]", request.getDistrict())
                .replace("[Blood Types]", String.join(", ", request.getBloodTypes()));

        // Find donors in the district with matching blood types
        List<Donor> donors = donorRepository.findByDistrictAndBloodGroupIn(
                request.getDistrict(),
                request.getBloodTypes()
        );

        // Send email to each donor
        for (Donor donor : donors) {
            sendEmail(donor.getEmail(), request.getSubject(), emailBody);
        }

        return savedAlert;
    }

    private void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("your-email@example.com"); // Replace with your sender email
            mailSender.send(message);
        } catch (Exception e) {
            // Log the error but don't fail the alert creation
            System.err.println("Failed to send email to " + to + ": " + e.getMessage());
        }
    }

    @Override
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    @Override
    public Alert getAlertById(Long id) {
        return alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
    }
}