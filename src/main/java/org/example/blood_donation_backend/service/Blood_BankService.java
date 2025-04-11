package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.Blood_BankDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;

public interface Blood_BankService {
    

    int saveBloodBank(Blood_BankDTO bloodBankDTO);

    Object getAllBloodBanks();

    ResponseDTO updateBloodBank(Blood_BankDTO bloodBankDTO);

    Blood_BankDTO getBloodBankByHospitalName(String hospitalName);

    ResponseDTO updateLastDonatedDate(Blood_BankDTO bloodBankDTO); // Changed method name
}
