package org.example.blood_donation_backend.controller;

import jakarta.validation.Valid;
import org.example.blood_donation_backend.QrGenerator.GenerateQr;
import org.example.blood_donation_backend.dto.AuthDTO;
import org.example.blood_donation_backend.dto.DonorDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.dto.UserDTO;
import org.example.blood_donation_backend.entity.Donor;
import org.example.blood_donation_backend.repo.DonorRepository;
import org.example.blood_donation_backend.service.DonorService;
import org.example.blood_donation_backend.service.UserService;
import org.example.blood_donation_backend.util.JwtUtil;
import org.example.blood_donation_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/donor")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DonorController {
    @Autowired
    private final DonorService donorService;

    @Autowired
    private DonorRepository donorRepository;

    //Constructor injection
    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll() {
        return new ResponseEntity<>(donorService.getAllDonors(), HttpStatus.OK);

    }

    @PutMapping("/update")
    public ResponseDTO updateDonor(@RequestBody @Valid DonorDTO donorDTO) {
        Donor donor = (Donor) donorRepository.findByContact(donorDTO.getContact()).orElse(null);

        if (donor != null) {
            // Update donor fields
            donor.setFullName(donorDTO.getFullName());
            donor.setDateOfBirth(donorDTO.getDateOfBirth());
            donor.setGender(donorDTO.getGender());
            donor.setBloodGroup(donorDTO.getBloodGroup());
            donor.setNicOrPassport(donorDTO.getNicOrPassport());
            donor.setEmail(donorDTO.getEmail());
            donor.setAddress(donorDTO.getAddress());
            donor.setCity(donorDTO.getCity());
            donor.setDistrict(donorDTO.getDistrict());
            donor.setProvince(donorDTO.getProvince());
            donor.setZipCode(donorDTO.getZipCode());

            System.out.println(donor);

            donorRepository.save(donor); // Save the updated donor to the repository

            return new ResponseDTO(VarList.Created, "Donor Updated Successfully", donor);
        } else {
            return new ResponseDTO(VarList.Not_Found, "Donor Not Found", null);
        }
    }

    @PutMapping("update-date")
    public ResponseDTO updateDonorDate(@RequestBody @Valid DonorDTO donorDTO) {
        Donor donor = (Donor) donorRepository.findByContact(donorDTO.getContact()).orElse(null);

        if (donor != null) {
            // Update donor fields
            donor.setDonationDate(donorDTO.getDonationDate());

            donorRepository.save(donor); // Save the updated donor to the repository

            return new ResponseDTO(VarList.Created, "Donor Updated Successfully", donor);
        } else {
            return new ResponseDTO(VarList.Not_Found, "Donor Not Found", null);
        }
    }


    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> registerDonor(@RequestBody @Valid DonorDTO donorDTO) {
        System.out.println("register donor");
        try {
            int res = donorService.saveDonor(donorDTO);
            System.out.println(donorDTO+"3");
            switch (res) {
                case VarList.Created -> {

                    GenerateQr.setData(donorDTO.getEmail(),donorDTO.getNicOrPassport()); // Set QR code data

                    // Removed token generation code
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Donor Registered Successfully", donorDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }
}

/*
    @PostMapping(value = "/register")
*/
/*
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO) {
        System.out.println("register");
*/
/*
        try {
            // Auto-generate Donor ID if user type is donor
            if (userDTO.getRole().equalsIgnoreCase("donor")) {
                String donorId = "DON-" + UUID.randomUUID().toString(); // Generate a unique donor ID
                userDTO.setId(donorId);

                int res = userService.saveUser(userDTO);
                switch (res) {
                    case VarList.Created -> {
                        String token = jwtUtil.generateToken(userDTO);
                        AuthDTO authDTO = new AuthDTO();
                        authDTO.setEmail(userDTO.getEmail());
                        authDTO.setToken(token);

                        // QR code generation logic
                        String qrCodePath = "C:/QR_Codes/" + donorId + ".png";
                        GenerateQr.setData(donorId,DonorDTO.getFullName(), DonorDTO.getEmail(),  qrCodePath);

                        // Email sending logic
                        emailService.sendMailWithAttachment(userDTO.getEmail(), "Registration Successful",
                                "Your registration is successful. Find your QR code attached.", qrCodePath);

                        return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ResponseDTO(VarList.Created, "Donor Registered Successfully", authDTO));
                    }
                    case VarList.Not_Acceptable -> {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                                .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                    }
                    default -> {
                        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                                .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                    }
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.Bad_Request, "Invalid User Type", null));
            }
        } catch (Exception e) {
*//*
*/
/*
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Exception Occurred: " + e.getMessage(), null));
        }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }*//*

    }
*/



