package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.dto.BloodCountDTO;
import org.example.blood_donation_backend.dto.Blood_BankDTO;
import org.example.blood_donation_backend.dto.HospitalDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.entity.Blood_Bank;
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
import java.util.Random;
import java.util.stream.Collectors;

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
    public List<Blood_BankDTO> getBloodBankStockDetails() {
        List<Blood_Bank> bloodBanks = blood_bankRepository.findAll();
        return bloodBanks.stream()
                .map(bloodBank -> new Blood_BankDTO(bloodBank.getHospitalName(), bloodBank.getStocklevels()))
                .collect(Collectors.toList());
    }
    @Override
    public List<BloodCountDTO> getBloodCounts(String hospitalName, List<String> bloodTypes) {
        Blood_Bank bloodBank = blood_bankRepository.findByHospitalName(hospitalName.trim())
                .orElseThrow(() -> new RuntimeException("Blood bank not found for hospital: " + hospitalName));

        // No changes to BloodBank; use stockLevels as-is
        Random random = new Random();
        return bloodTypes.stream()
                .filter(type -> bloodBank.getStocklevels().contains(type))
                .map(type -> {
                    BloodCountDTO dto = new BloodCountDTO();
                    dto.setBloodType(type);
                    // Mock counts since stockLevels only lists types
                    dto.setCount(random.nextInt(200)); // Random 0-200 units
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDTO updateBloodBank(Blood_BankDTO bloodBankDTO) {
        Blood_Bank bloodBank = blood_bankRepository.findByHospitalName(bloodBankDTO.getHospitalName())
                .orElseThrow(() -> new RuntimeException("Blood bank not found"));

        if (bloodBankDTO.getLastStockUpdateDate() != null) {
            bloodBank.setLastStockUpdateDate(bloodBankDTO.getLastStockUpdateDate());
        }
        if (bloodBankDTO.getStocklevels() != null && !bloodBankDTO.getStocklevels().isEmpty()) {
            bloodBank.setStocklevels(bloodBankDTO.getStocklevels());
        }

        blood_bankRepository.save(bloodBank);
        return new ResponseDTO(VarList.Created, "Blood bank updated", bloodBankDTO);    }

    @Override
    public Blood_BankDTO getBloodBankByHospitalName(String hospitalName) {
        Blood_Bank bloodBank = blood_bankRepository.findByHospitalName(hospitalName)
                .orElseThrow(() -> new RuntimeException("Blood bank not found"));
        // Map entity to DTO (simplified here; use a mapper in practice)
        Blood_BankDTO dto = new Blood_BankDTO();
        dto.setHospitalName(bloodBank.getHospitalName());
        dto.setStocklevels(bloodBank.getStocklevels());
        dto.setLastStockUpdateDate(bloodBank.getLastStockUpdateDate());
        return dto;
    }
    @Override
    public ResponseDTO updateLastDonatedDate(Blood_BankDTO blood_bankDTO) {
        Blood_Bank bloodBank = blood_bankRepository.findByHospitalName(blood_bankDTO.getHospitalName())
                .orElseThrow(() -> new RuntimeException("Blood bank not found"));

        if (blood_bankDTO.getLastStockUpdateDate() != null) {
            bloodBank.setLastStockUpdateDate(blood_bankDTO.getLastStockUpdateDate());
        }
        if (blood_bankDTO.getStocklevels() != null && !blood_bankDTO.getStocklevels().isEmpty()) {
            bloodBank.setStocklevels(blood_bankDTO.getStocklevels());
        }

        blood_bankRepository.save(bloodBank);
        return new ResponseDTO(VarList.Created, "Blood bank updated", blood_bankDTO);
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
