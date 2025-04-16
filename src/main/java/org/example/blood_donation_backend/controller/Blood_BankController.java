package org.example.blood_donation_backend.controller;

import jakarta.validation.Valid;
import org.example.blood_donation_backend.dto.Blood_BankDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.entity.Blood_Bank;
import org.example.blood_donation_backend.entity.Hospital;
import org.example.blood_donation_backend.repo.Blood_BankRepository;
import org.example.blood_donation_backend.repo.HospitalRepository;
import org.example.blood_donation_backend.service.Blood_BankService;
import org.example.blood_donation_backend.service.impl.Blood_BankServiceImpl;
import org.example.blood_donation_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blood_bank")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Blood_BankController {
    @Autowired
    private Blood_BankService blood_bankService;
    @Autowired
    private Blood_BankRepository blood_bankRepository;
    @Autowired
    private HospitalRepository hospitalRepository;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll() {
/*
        return new ResponseEntity<>("",blood_bankService.getAllBloodBanks(), HttpStatus.OK);
*/
        ResponseEntity responseEntity = new ResponseEntity<>(blood_bankService.getAllBloodBanks(), HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/hospital-names")
    public ResponseEntity<ResponseDTO> getAllHospitalNames() {
        List<String> hospitalNames = hospitalRepository.findAll()
                .stream()
                .map(Hospital::getHospitalName) // Extract only the hospital name
                .toList();

        if (!hospitalNames.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.Accepted, "Hospital Name List Retrieved Successfully", hospitalNames));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO(VarList.Not_Found, "No Hospitals Found", null));
        }
    }

    @GetMapping("/stock-details")
    public ResponseEntity<ResponseDTO> getBloodBankStockDetails() {
        try {
            List<Blood_BankDTO> stockDetails = blood_bankService.getBloodBankStockDetails();
            if (!stockDetails.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.Accepted, "Blood Bank Stock Details Retrieved Successfully", stockDetails));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "No Blood Banks Found", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Error Retrieving Stock Details", e.getMessage()));
        }
    }    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerBloodBank(@RequestBody @Valid Blood_BankDTO blood_bankDTO) {
        System.out.println("registerBloodBank");
            try {
                int res = blood_bankService.saveBloodBank(blood_bankDTO);

                switch (res) {
                    case VarList.Created -> {
                        return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ResponseDTO(VarList.Created, "Blood Bank Registered Successfully", blood_bankDTO));
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
            }catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        }



    @PutMapping("/update-last-donated-date")
    public ResponseDTO updateLastDonatedDate(@RequestBody @Valid Blood_BankDTO blood_bankDTO) {
        return blood_bankService.updateLastDonatedDate(blood_bankDTO);
    }

}
