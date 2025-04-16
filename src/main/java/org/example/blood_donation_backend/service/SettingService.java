package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.entity.Setting;

public interface SettingService {
    Setting getSettings();

    Setting updateSettings(int notificationThreshold, String notificationFrequency);
}
