package org.example.blood_donation_backend.controller;

import jakarta.validation.Valid;
import org.example.blood_donation_backend.dto.Blood_InventoryDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.service.impl.Blood_InventoryServiceImpl;
import org.example.blood_donation_backend.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/blood-inventory")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class
Blood_InventoryController {
    @Autowired
    private Blood_InventoryServiceImpl bloodInventoryService;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAll() {
        return new ResponseEntity<>(bloodInventoryService.getAll(), HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid Blood_InventoryDTO blood_inventoryDTO) {
        System.out.println("register blood inventory");
        try {
            int res = bloodInventoryService.save(blood_inventoryDTO);
            switch (res) {
                case VarList.Created -> {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Blood Inventory Registered Successfully", blood_inventoryDTO));
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
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }
    @PutMapping("/update/{id}")
    private ResponseEntity<ResponseDTO> update(@PathVariable UUID id, @RequestBody @Valid Blood_InventoryDTO blood_inventoryDTO) {
        return new ResponseEntity<>(bloodInventoryService.update(id, blood_inventoryDTO), HttpStatus.OK);
    }

  /*  @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable UUID id) {
        return new ResponseEntity<>(bloodInventoryService.delete(id), HttpStatus.OK);
    }*/

}
