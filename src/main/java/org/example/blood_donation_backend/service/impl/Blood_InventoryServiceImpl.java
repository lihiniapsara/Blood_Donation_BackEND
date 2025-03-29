package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.dto.Blood_InventoryDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.entity.Blood_Inventory;
import org.example.blood_donation_backend.repo.Blood_InventoryRepository;
import org.example.blood_donation_backend.service.Blood_InventoryService;
import org.example.blood_donation_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class Blood_InventoryServiceImpl implements Blood_InventoryService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Blood_InventoryRepository blood_InventoryRepository;

    public ResponseDTO getAll() {
        try {
            List<Blood_Inventory> blood_inventories = blood_InventoryRepository.findAll();
            if (blood_inventories.isEmpty()) {
                return new ResponseDTO(VarList.No_Content, "No Blood Inventories Found", null);
            } else {
                return new ResponseDTO(VarList.Created, "Blood Inventories Fetched Successfully", blood_inventories);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(VarList.Bad_Gateway, "An Unexpected Error Occurred", null);
        }

    }

    @Override
    public int save(Blood_InventoryDTO blood_inventoryDTO) {
        blood_InventoryRepository.save(modelMapper.map(blood_inventoryDTO, Blood_Inventory.class));
        return VarList.Created;
    }

    @Override
    public ResponseDTO update(UUID id, Blood_InventoryDTO bloodInventoryDTO) {
        try {
            if (blood_InventoryRepository.existsById(id)) {
                Blood_Inventory existingInventory = blood_InventoryRepository.findById(String.valueOf(id)).orElse(null);

                if (existingInventory != null) {
                    // Map new data to existing entity
                    existingInventory.setBloodType(bloodInventoryDTO.getBloodType());
                    existingInventory.setUnits(bloodInventoryDTO.getUnits());
                    existingInventory.setExpiryDate(bloodInventoryDTO.getExpiryDate());

                    // Save updated entity
                    blood_InventoryRepository.save(existingInventory);
                    return new ResponseDTO(VarList.Created, "Blood Inventory Updated Successfully", existingInventory);
                } else {
                    return new ResponseDTO(VarList.Not_Found, "Blood Inventory Not Found", null);
                }
            } else {
                return new ResponseDTO(VarList.Not_Found, "Blood Inventory Not Found", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(VarList.Bad_Gateway, "An Unexpected Error Occurred", null);
        }
    }

    @Override
    public ResponseDTO delete(UUID id) {
        try {
            if (blood_InventoryRepository.existsById(String.valueOf(id))) {
                return new ResponseDTO(VarList.Created, "Blood Inventory Deleted Successfully", null);
            } else {
                return new ResponseDTO(VarList.Not_Found, "Blood Inventory Not Found", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(VarList.Bad_Gateway, "An Unexpected Error Occurred", null);
        }
    }

}
