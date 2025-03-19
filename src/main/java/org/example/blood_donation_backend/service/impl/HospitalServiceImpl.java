package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.dto.HospitalDTO;
import org.example.blood_donation_backend.entity.Donor;
import org.example.blood_donation_backend.entity.Hospital;
import org.example.blood_donation_backend.repo.HospitalRepository;
import org.example.blood_donation_backend.service.HospitalService;
import org.example.blood_donation_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public int saveHospital(HospitalDTO hospitalDTO) {
        if (hospitalRepository.existsByOfficialEmail(hospitalDTO.getOfficialEmail())) {
            return VarList.Not_Acceptable;
        }else {
            hospitalRepository.save(modelMapper.map(hospitalDTO, Hospital.class));
            return VarList.Created;
        }
    }
}
