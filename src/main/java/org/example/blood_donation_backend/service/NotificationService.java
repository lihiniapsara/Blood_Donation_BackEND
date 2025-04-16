package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.entity.Donor;
import org.example.blood_donation_backend.entity.Hospital;
import org.example.blood_donation_backend.entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotifications();

    Notification sendNotification(String hospitalName, String bloodGroup, String template);

    void checkLowBloodLevels(Hospital hospital);

    void sendAutomatedNotification(Hospital hospital, String bloodGroup, String template);

    void sendToDonors(List<Donor> donors, String message);

    String generateMessage(String template, String hospitalName, String bloodGroup);

    int getBloodUnits(Hospital hospital, String bloodGroup);
}
