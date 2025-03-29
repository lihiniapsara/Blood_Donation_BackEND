package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.dto.CampDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.entity.Camp;
import org.example.blood_donation_backend.entity.Hospital;
import org.example.blood_donation_backend.repo.CampRepository;
import org.example.blood_donation_backend.repo.HospitalRepository;
import org.example.blood_donation_backend.service.CampService;
import org.example.blood_donation_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Service
@Transactional
public class CampServiceImpl implements CampService {
    @Autowired
    private CampRepository campRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public int saveCamp(CampDTO campDTO) {
        System.out.println("Starting saveCamp with DTO: " + campDTO);

        // Check for duplicate email
        if (campRepository.existsByEmail(campDTO.getEmail())) {
            System.out.println("Email already exists: " + campDTO.getEmail());
            return VarList.Not_Acceptable;
        }

        // Fetch hospital
        Hospital hospital = hospitalRepository.findByHospitalName(campDTO.getHospitalname());
        System.out.println("Hospital fetched for name '" + campDTO.getHospitalname() + "': " + hospital);
        if (hospital == null) {
            System.out.println("Hospital not found in database: " + campDTO.getHospitalname());
            return VarList.Not_Acceptable; // Or a custom error code like VarList.Hospital_Not_Found
        }

        try {
            // Create Camp entity
            Camp camp = new Camp(
                    hospital,
                    campDTO.getCampDate(),
                    campDTO.getCampName(),
                    campDTO.getContactNumber(),
                    campDTO.getEmail(),
                    campDTO.getCampLocation(),
                    campDTO.getCity(),
                    campDTO.getDistrict(),
                    campDTO.getProvince(),
                    campDTO.getZipCode()
            );
            System.out.println("Camp created: " + camp);
            System.out.println("Hospital in Camp: " + camp.getHospital());

            // Save to database
            Camp savedCamp = campRepository.save(camp);
            System.out.println("Camp saved: " + savedCamp);

            sendEmail(camp.getEmail(), camp.getCampid());
            return VarList.Created;

        } catch (Exception e) {
            System.out.println("Exception during save: " + e.getMessage());
            e.printStackTrace();
            return VarList.Not_Acceptable;
        }
    }

    public void sendEmail(String email, UUID code) {
        System.out.println("Sending email...");

        new Thread(() -> {
            try {
                String senderEmail = "apsaralihini11@gmail.com";
                String senderPassword = "nlthclganmfhevbv"; // Replace with app-specific password

                String subject = "OTP Code & Camp ID from Blood Donation App";

                String body = "Dear User,\n\n" +
                        "Your OTP code for accessing Blood Donation services is: " + code + "\n\n" +
                        "Your Camp ID: "  + "\n\n" +
                        "Please use this information to verify your identity. The OTP is valid for 10 minutes only.\n" +
                        "If you did not request this OTP, please ignore this email or contact support.\n\n" +
                        "Best regards,\n" +
                        "The Blood Donation Team";

                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");

                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, senderPassword);
                    }
                });

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(senderEmail));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                    message.setSubject(subject);
                    message.setText(body);

                    Transport.send(message);

                    System.out.println("OTP and Camp ID sent successfully to " + email);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    @Override
    public ResponseDTO getAll() {
        try {
            List<Camp> camps = campRepository.findAll(); // Fetch all hospitals
            if (camps.isEmpty()) {
                return new ResponseDTO(VarList.No_Content, "No Camps Found", null);
            }
            return new ResponseDTO(VarList.Created, "Camps Retrieved Successfully", camps);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(VarList.Bad_Gateway, "An Unexpected Error Occurred", null);
        }
    }

    @Override
    public ResponseEntity<ResponseDTO> updateCamp(CampDTO campDTO) {
        if (campRepository.existsByEmail(campDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
        } else {
            Camp camp = modelMapper.map(campDTO, Camp.class);
            campRepository.save(camp);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Camp Updated Successfully", camp));
        }

    }
}
