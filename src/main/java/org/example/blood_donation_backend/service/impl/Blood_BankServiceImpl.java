package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.dto.Blood_BankDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.entity.Blood_Bank;
import org.example.blood_donation_backend.entity.Camp;
import org.example.blood_donation_backend.repo.Blood_BankRepository;
import org.example.blood_donation_backend.service.Blood_BankService;
import org.example.blood_donation_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class Blood_BankServiceImpl implements Blood_BankService {
    @Autowired
    private Blood_BankRepository blood_bankRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Object getAllBloodBanks() {
        List<Blood_Bank> blood_banks = blood_bankRepository.findAll();
        return new ResponseEntity<>(new ResponseDTO(VarList.Created, "Blood Banks Fetched Successfully", blood_banks), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseDTO> updateBloodBank(Blood_BankDTO bloodBankDTO) {
        Blood_Bank blood_bank = blood_bankRepository.findByHospitalName(bloodBankDTO.getHospitalName());
        if (blood_bank == null) {
            blood_bank.setHospitalName(bloodBankDTO.getHospitalName());
            blood_bank.setLocation(bloodBankDTO.getLocation());
            blood_bank.setContactDetails(bloodBankDTO.getContactDetails());
            blood_bank.setStorageCapacity(bloodBankDTO.getStorageCapacity());
            blood_bank.setLastStockUpdateDate(bloodBankDTO.getLastStockUpdateDate());
            blood_bank.setManagementType(bloodBankDTO.getManagementType());
            blood_bankRepository.save(blood_bank);

            System.out.println(blood_bank);
            return new ResponseEntity<>(new ResponseDTO(VarList.Created, "Blood Bank Updated Successfully", blood_bank), HttpStatus.OK);
        }else {
return new ResponseEntity<>(new ResponseDTO(VarList.Not_Acceptable, "Blood Bank Already Exists", null), HttpStatus.OK);
        }
    }

    @Override
    public int saveBloodBank(Blood_BankDTO bloodBankDTO) {
        if (blood_bankRepository.existsByHospitalName(bloodBankDTO.getHospitalName())) {
            return VarList.Not_Acceptable;
        }else {
            blood_bankRepository.save(modelMapper.map(bloodBankDTO, Blood_Bank.class));
            System.out.println(bloodBankDTO+"v");
            return VarList.Created;
        }
    }
}
/*if (campRepository.existsByEmail(campDTO.getEmail())) {
        return VarList.Not_Acceptable;
        } else {
Camp camp = modelMapper.map(campDTO, Camp.class);
            campRepository.save(camp);

sendEmail(camp.getEmail(), camp.getCampid());
*//*
            campRepository.save(modelMapper.map(campDTO, Camp.class));
*//*
        return VarList.Created;*/
