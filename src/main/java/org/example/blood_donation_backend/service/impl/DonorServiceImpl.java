package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.dto.DonorDTO;
import org.example.blood_donation_backend.entity.Donor;
import org.example.blood_donation_backend.repo.DonorRepository;
import org.example.blood_donation_backend.service.DonorService;
import org.example.blood_donation_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DonorServiceImpl implements DonorService {
    @Autowired
    private DonorRepository donorRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override




    public int saveDonor(DonorDTO donorDTO) {
        if (donorRepository.existsByEmail(donorDTO.getEmail())) {
            return VarList.Not_Acceptable;
        }else {
            donorRepository.save(modelMapper.map(donorDTO, Donor.class));
            return VarList.Created;
        }
    }
}
