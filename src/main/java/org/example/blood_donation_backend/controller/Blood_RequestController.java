package org.example.blood_donation_backend.controller;

import org.example.blood_donation_backend.dto.Blood_RequestDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.entity.Blood_Request;
import org.example.blood_donation_backend.service.impl.Blood_RequestServiceImpl;

import org.example.blood_donation_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/blood_request")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Blood_RequestController {
    @Autowired
    private Blood_RequestServiceImpl blood_requestService;


    @GetMapping("/getAll")
    public ResponseEntity<List<Blood_Request>> getAllRequests() {

        return ResponseEntity.ok(blood_requestService.getAllBloodRequests());
    }
   /* @GetMapping
    public ResponseEntity<List<Blood_RequestDTO>> getAllBloodRequests() {
        List<Blood_RequestDTO> bloodRequests = blood_requestService.getAllBloodRequest();
        return ResponseEntity.ok(bloodRequests);
    }
*/
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody Blood_RequestDTO blood_requestDTO) {
        System.out.println("register blood request");
        try {
            System.out.println(blood_requestDTO.getHospitalName());
            int res = blood_requestService.save(blood_requestDTO);
            switch (res) {
                case VarList.Created -> {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Blood Request Registered Successfully", blood_requestDTO));
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
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }
}
