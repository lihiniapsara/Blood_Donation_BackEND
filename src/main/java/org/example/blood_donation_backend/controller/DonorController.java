package org.example.blood_donation_backend.controller;

import jakarta.validation.Valid;
import org.example.blood_donation_backend.QrGenerator.GenerateQr;
import org.example.blood_donation_backend.dto.AuthDTO;
import org.example.blood_donation_backend.dto.DonorDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.dto.UserDTO;
import org.example.blood_donation_backend.service.DonorService;
import org.example.blood_donation_backend.service.UserService;
import org.example.blood_donation_backend.util.JwtUtil;
import org.example.blood_donation_backend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/donor")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DonorController {
    private final DonorService donorService;

    //Constructor injection
    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("test");
        return "test";
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> registerDonor(@RequestBody @Valid DonorDTO donorDTO) {
        System.out.println("register donor");
        try {
            int res = donorService.saveDonor(donorDTO);
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



