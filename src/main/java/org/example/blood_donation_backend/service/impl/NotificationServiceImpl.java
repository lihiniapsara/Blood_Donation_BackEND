package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.entity.Donor;
import org.example.blood_donation_backend.entity.Hospital;
import org.example.blood_donation_backend.entity.Notification;
import org.example.blood_donation_backend.entity.Setting;
import org.example.blood_donation_backend.repo.DonorRepository;
import org.example.blood_donation_backend.repo.HospitalRepository;
import org.example.blood_donation_backend.repo.NotificationRepository;
import org.example.blood_donation_backend.repo.SettingRepository;
import org.example.blood_donation_backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification sendNotification(String hospitalName, String bloodGroup, String template) {
        // Find hospital by hospitalName
        Hospital hospital = hospitalRepository.findByHospitalName(hospitalName);
        if (hospital == null) {
            throw new RuntimeException("Hospital not found with name: " + hospitalName);
        }

        // Fetch donors with matching blood group in the hospital's district
        List<Donor> donors = donorRepository.findByDistrictAndBloodGroupIn(
                hospital.getDistrict(),
                List.of(bloodGroup)
        );
        int recipientCount = donors.size();
        String message = generateMessage(template, hospital.getHospitalName(), bloodGroup);

        // Simulate sending notifications to donors
        sendToDonors(donors, message);

        Notification notification = new Notification();
        notification.setHospital(hospital);
        notification.setBloodGroup(bloodGroup);
        notification.setMessage(message);
        notification.setTemplate(template);
        notification.setStatus("sent");
        notification.setRecipientCount(recipientCount);
        notification.setCreatedAt(LocalDateTime.now());

        return notificationRepository.save(notification);
    }

    @Override
    public void checkLowBloodLevels(Hospital hospital) {
        Optional<Setting> optionalSetting = settingRepository.findAll().stream().findFirst();
        int threshold = optionalSetting.map(Setting::getNotificationThreshold).orElse(30);

        hospital.getAvailableBloodGroups().forEach(bloodGroup -> {
            int units = getBloodUnits(hospital, bloodGroup); // Placeholder
            if (units <= threshold * 0.3) {
                sendAutomatedNotification(hospital, bloodGroup, "urgent");
            } else if (units <= threshold) {
                sendAutomatedNotification(hospital, bloodGroup, "standard");
            }
        });
    }

    @Override
    public void sendAutomatedNotification(Hospital hospital, String bloodGroup, String template) {
        List<Donor> donors = donorRepository.findByDistrictAndBloodGroupIn(
                hospital.getDistrict(),
                List.of(bloodGroup)
        );
        int recipientCount = donors.size();
        String message = generateMessage(template, hospital.getHospitalName(), bloodGroup);

        sendToDonors(donors, message);

        Notification notification = new Notification();
        notification.setHospital(hospital);
        notification.setBloodGroup(bloodGroup);
        notification.setMessage(message);
        notification.setTemplate(template);
        notification.setStatus("automated");
        notification.setRecipientCount(recipientCount);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    @Override
    public void sendToDonors(List<Donor> donors, String message) {
        donors.forEach(donor -> {
            System.out.println("Sending notification to " + donor.getEmail() + ": " + message);
        });
    }

    @Override
    public String generateMessage(String template, String hospitalName, String bloodGroup) {
        return switch (template) {
            case "urgent" -> "Urgent: " + hospitalName + " critically needs " + bloodGroup + " blood donations. Please donate immediately!";
            case "standard" -> hospitalName + " requests " + bloodGroup + " blood donations to maintain supply levels.";
            case "scheduled" -> hospitalName + " is organizing a " + bloodGroup + " blood donation drive. Please participate!";
            default -> "";
        };
    }

    @Override
    public int getBloodUnits(Hospital hospital, String bloodGroup) {
        return (int) (Math.random() * 50); // Placeholder
    }
}