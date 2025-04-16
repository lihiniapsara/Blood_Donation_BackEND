package org.example.blood_donation_backend.controller;

import org.example.blood_donation_backend.dto.NotificationRequestDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.entity.Notification;
import org.example.blood_donation_backend.service.NotificationService;
import org.example.blood_donation_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        if (notifications.isEmpty()) {
            return ResponseEntity.ok(new ResponseDTO(VarList.No_Content, "No Notifications Found", null));
        }
        return ResponseEntity.ok(new ResponseDTO(VarList.Created, "Notifications Retrieved Successfully", notifications));
    }

    @PostMapping("/send")
    public ResponseEntity<ResponseDTO> sendNotification(@RequestBody NotificationRequestDTO request) {
        if (request.getHospitalName() == null || request.getHospitalName().trim().isEmpty()) {
            return ResponseEntity.status(VarList.Bad_Request)
                    .body(new ResponseDTO(VarList.Bad_Request, "Hospital name is required", null));
        }
        try {
            Notification notification = notificationService.sendNotification(
                    request.getHospitalName(),
                    request.getBloodGroup(),
                    request.getTemplate()
            );
            return ResponseEntity.status(VarList.Created)
                    .body(new ResponseDTO(VarList.Created, "Notification Sent Successfully", notification));
        } catch (RuntimeException e) {
            return ResponseEntity.status(VarList.Bad_Request)
                    .body(new ResponseDTO(VarList.Bad_Request, e.getMessage(), null));
        }
    }
}