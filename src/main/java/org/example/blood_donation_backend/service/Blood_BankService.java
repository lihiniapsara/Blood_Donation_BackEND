package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.Blood_BankDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface Blood_BankService {
    

    int saveBloodBank(Blood_BankDTO bloodBankDTO);

    Object getAllBloodBanks();

    ResponseEntity<ResponseDTO> updateBloodBank(Blood_BankDTO bloodBankDTO);
}
