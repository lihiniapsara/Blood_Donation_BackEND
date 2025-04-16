package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.BloodCountDTO;
import org.example.blood_donation_backend.dto.Blood_BankDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;

import java.util.List;

public interface Blood_BankService {

    List<Blood_BankDTO> getBloodBankStockDetails();
    int saveBloodBank(Blood_BankDTO bloodBankDTO);

    Object getAllBloodBanks();

    List<BloodCountDTO> getBloodCounts(String hospitalName, List<String> bloodTypes);

    ResponseDTO updateBloodBank(Blood_BankDTO bloodBankDTO);

    Blood_BankDTO getBloodBankByHospitalName(String hospitalName);
    ResponseDTO updateLastDonatedDate(Blood_BankDTO bloodBankDTO); // Changed method name
}
