package org.example.blood_donation_backend.controller;

import jakarta.validation.Valid;
import org.example.blood_donation_backend.dto.HospitalDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.dto.UserDTO;
import org.example.blood_donation_backend.entity.User;
import org.example.blood_donation_backend.service.HospitalService;
import org.example.blood_donation_backend.service.UserService;
import org.example.blood_donation_backend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/hospitals")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class HospitalController {

    private final HospitalService hospitalService;
    private final UserService userService;

    public HospitalController(HospitalService hospitalService, UserService userService) {
        this.hospitalService = hospitalService;
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll() {
        return new ResponseEntity<>(hospitalService.getAllHospitals(), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> registerHospital(@RequestParam("hospitalName") String hospitalname, /* ... other params ... */
                                                        @RequestParam("typeOfHospital") String typeOfHospital,
                                                        @RequestParam("registrationNumber") String registrationNumber,
                                                        @RequestParam("yearOfEstablishment") Integer yearOfEstablishment,
                                                        @RequestParam("address") String address,
                                                        @RequestParam("city") String city,
                                                        @RequestParam("district") String district,
                                                        @RequestParam("province") String province,
                                                        @RequestParam("zipCode") String zipCode,
                                                        @RequestParam("officialEmail") String officialEmail,
                                                        @RequestParam("contactNumber") String contactNumber,
                                                        @RequestParam("emergencyContactNumber") String emergencyContactNumber,
                                                        @RequestParam("website") String website,
                                                        @RequestParam("hasBloodBank") Boolean hasBloodBank,
                                                        @RequestParam("bloodBankContactPersonName") String bloodBankContactPersonName,
                                                        @RequestParam("bloodBankContactNumber") String bloodBankContactNumber,
                                                        @RequestParam("availableBloodGroups") List<String> availableBloodGroups,
                                                        @RequestParam("bloodBankLicenseNumber") String bloodBankLicenseNumber,
                                                        @RequestParam("healthMinistryApprovalCertificate") List<MultipartFile> imageFiles,
                                                        @RequestParam("emergencyBloodServiceAvailable") Boolean emergencyBloodServiceAvailable,
                                                        @RequestParam("bloodDonationCampFacility") Boolean bloodDonationCampFacility,
                                                        @RequestParam("numberOfBloodStorageUnits") Integer numberOfBloodStorageUnits,
                                                        @RequestParam("userName") String username) {
        System.out.println("Registering hospital...");

        try {

            // Create a new HospitalDTO and set the hospital details
            HospitalDTO hospitalDTO = new HospitalDTO();
            hospitalDTO.setHospitalName(hospitalname);
            hospitalDTO.setTypeOfHospital(typeOfHospital);
            hospitalDTO.setRegistrationNumber(registrationNumber);
            hospitalDTO.setYearOfEstablishment(yearOfEstablishment);
            hospitalDTO.setAddress(address);
            hospitalDTO.setCity(city);
            hospitalDTO.setDistrict(district);
            hospitalDTO.setProvince(province);
            hospitalDTO.setZipCode(zipCode);
            hospitalDTO.setOfficialEmail(officialEmail);
            hospitalDTO.setContactNumber(contactNumber);
            hospitalDTO.setEmergencyContactNumber(emergencyContactNumber);
            hospitalDTO.setWebsite(website);
            hospitalDTO.setHasBloodBank(hasBloodBank);
            hospitalDTO.setBloodBankContactPersonName(bloodBankContactPersonName);
            hospitalDTO.setBloodBankContactNumber(bloodBankContactNumber);
            hospitalDTO.setAvailableBloodGroups(availableBloodGroups);
            hospitalDTO.setBloodBankLicenseNumber(bloodBankLicenseNumber);
            hospitalDTO.setEmergencyBloodServiceAvailable(emergencyBloodServiceAvailable);
            hospitalDTO.setBloodDonationCampFacility(bloodDonationCampFacility);
            hospitalDTO.setNumberOfBloodStorageUnits(numberOfBloodStorageUnits);

            // Set the user to the hospitalDTO
            hospitalDTO.setUserName(username);

            System.out.println(hospitalDTO);

            // Handle image files (Health Ministry Approval Certificates)
            List<String> imagePaths = new ArrayList<>();
            if (imageFiles != null && !imageFiles.isEmpty()) {
                for (MultipartFile file : imageFiles) {
                    if (!file.isEmpty()) {
                        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                        String uploadDir = "uploads/hospital/";

                        File directory = new File(uploadDir);
                        if (!directory.exists() && !directory.mkdirs()) {
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(new ResponseDTO(VarList.Bad_Gateway, "Failed to create upload directory", null));
                        }

                        Path path = Paths.get(uploadDir + filename);
                        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                        imagePaths.add(uploadDir + filename);
                    }
                }
            }
            hospitalDTO.setHealthMinistryApprovalCertificate(imagePaths);

            // Save the hospital
            int res = hospitalService.saveHospital(hospitalDTO);

            // Return response based on result
            return switch (res) {
                case VarList.Created -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.Created, "Hospital Registered Successfully", hospitalDTO));
                case VarList.Not_Acceptable -> ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .body(new ResponseDTO(VarList.Not_Acceptable, "Registration Number Already Used", null));
                default -> ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error occurred", null));
            };
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "An unexpected error occurred", null));
        }
    }

}
