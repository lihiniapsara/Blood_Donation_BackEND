package org.example.blood_donation_backend.controller;

import jakarta.validation.Valid;
import org.example.blood_donation_backend.dto.CampDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.service.CampService;
import org.example.blood_donation_backend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/camp")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CampController {
    private final CampService campService;

    public CampController(CampService campService) {
        this.campService = campService;
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("sd");
        return "dd";
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> registerCamp(@RequestBody @Valid CampDTO campDTO) {
        System.out.println("register camp");
        try {
            int res = campService.saveCamp(campDTO);
            switch (res) {
                case VarList.Created -> {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Camp Registered Successfully", campDTO));
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
