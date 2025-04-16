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
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final DonorRepository donorRepository;
    private final JavaMailSender mailSender;

    @Override
    public Alert createAlert(AlertRequestDTO request) {
        System.out.println("Received alert request: district=" + request.getDistrict() + ", bloodTypes=" + request.getBloodTypes());

        // Create the alert
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
        System.out.println("Alert object created: " + alert);

        // Replace placeholders in the email body
        String emailBody = request.getBody()
                .replace("[Hospital]", request.getHospital())
                .replace("[District]", request.getDistrict())
                .replace("[Blood Types]", String.join(", ", request.getBloodTypes()));
        System.out.println("Email body: " + emailBody);

        // Find donors in the district with matching blood types
        List<Donor> donors = donorRepository.findByDistrictAndBloodGroupIn(
                request.getDistrict(),
                request.getBloodTypes()
        );
        System.out.println("Found " + donors.size() + " donors: " + donors);

        // Collect emails of notified donors
        List<String> recipientEmails = new ArrayList<>();
        for (Donor donor : donors) {
            System.out.println("Processing donor: id=" + donor.getId() + ", email=" + donor.getEmail());
            if (donor.getEmail() != null && !donor.getEmail().isEmpty()) {
                System.out.println("Valid email found: " + donor.getEmail());
                sendEmail(donor.getEmail(), request.getSubject(), emailBody);
                recipientEmails.add(donor.getEmail());
            } else {
                System.out.println("Skipping donor with null/empty email: id=" + donor.getId());
            }
        }
        System.out.println("Collected recipientEmails: " + recipientEmails);

        // Set recipient emails in the alert
        alert.setRecipientEmails(recipientEmails);
        System.out.println("Setting recipientEmails: " + recipientEmails);

        // Save the alert with recipient emails
        Alert savedAlert = alertRepository.save(alert);
        System.out.println("Alert saved with id=" + savedAlert.getId() + ", recipientEmails=" + savedAlert.getRecipientEmails());
        return savedAlert;
    }

    private void sendEmail(String to, String subject, String body) {
        try {
            System.out.println("Attempting to send email to: " + to + ", subject: " + subject);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("apsaralihini11@gmail.com");
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + to);
        } catch (Exception e) {
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