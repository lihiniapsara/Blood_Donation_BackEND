package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.Blood_InventoryDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.entity.Blood_Inventory;
import org.example.blood_donation_backend.util.VarList;

import java.util.UUID;

public interface Blood_InventoryService {
    int save(Blood_InventoryDTO blood_inventoryDTO);

     ResponseDTO update(UUID id, Blood_InventoryDTO bloodInventoryDTO) ;

/*
    ResponseDTO delete(UUID id);
*/
}
