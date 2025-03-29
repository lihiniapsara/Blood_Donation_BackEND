package org.example.blood_donation_backend.controller;

import jakarta.validation.Valid;
import org.example.blood_donation_backend.dto.CampDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.entity.Hospital;
import org.example.blood_donation_backend.repo.CampRepository;
import org.example.blood_donation_backend.repo.HospitalRepository;
import org.example.blood_donation_backend.service.CampService;
import org.example.blood_donation_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/camp")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CampsController {
    @Autowired
    private final CampService campService;
    @Autowired
    private final CampRepository campRepository;
    @Autowired
    private final HospitalRepository hospitalRepository;

    public CampsController(CampService campService, CampRepository campRepository, HospitalRepository hospitalRepository) {
        this.campService = campService;
        this.campRepository = campRepository;
        this.hospitalRepository = hospitalRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll() {
        return new ResponseEntity<>(campService.getAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> registerCamp(@RequestBody @Valid CampDTO campDTO) {
        System.out.println("register camp");
        try {
            System.out.println("122222"+campDTO.getHospitalname());
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
        }
          catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ResponseDTO(VarList.Internal_Server_Error, "Error", null));
        }

    }

    // Method to get the hospital list
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



    /*public ResponseEntity<ResponseDTO> registerCamp(@RequestBody @Valid CampDTO campDTO) {
        System.out.println("register camp");
        try {
            UUID campId = campService.saveCamp(campDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO(VarList.Created, "Camp Registered Successfully", campId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }*/

    @PutMapping("/update")
    public ResponseDTO updateCamp(@RequestBody @Valid CampDTO campDTO) {
        System.out.println("update camp");

    /*    Camp camp = campRepository.findByCampid(camp.getCampid());

        if (camp != null) {  // ✅ Corrected Condition
            camp.setCampName(campDTO.getCampName());
            camp.setCampDate(campDTO.getCampDate());
            camp.setContactNumber(campDTO.getContactNumber());
            camp.setCity(campDTO.getCity());
            camp.setDistrict(campDTO.getDistrict());
            camp.setProvince(campDTO.getProvince());
            camp.setZipCode(campDTO.getZipCode());
            camp.setCampLocation(campDTO.getCampLocation());

            System.out.println("Updated Camp: " + camp);

            campRepository.save(camp);
            return new ResponseDTO(VarList.Created, "Camp Updated Successfully", camp);
        }*/

        return new ResponseDTO(VarList.Not_Acceptable, "Camp Not Found", null);
    }
}
