package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.entity.Setting;
import org.example.blood_donation_backend.repo.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingRepository settingRepository;
    @Override
    public Setting getSettings() {
        return settingRepository.findAll().stream().findFirst().orElseGet(() -> {
            Setting setting = new Setting();
            setting.setNotificationThreshold(30);
            setting.setNotificationFrequency("immediate");
            return settingRepository.save(setting);
        });
    }
    @Override
    public Setting updateSettings(int notificationThreshold, String notificationFrequency) {
        Setting setting = getSettings();
        setting.setNotificationThreshold(notificationThreshold);
        setting.setNotificationFrequency(notificationFrequency);
        return settingRepository.save(setting);
    }
}