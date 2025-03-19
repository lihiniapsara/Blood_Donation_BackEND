package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.dto.CampDTO;
import org.example.blood_donation_backend.entity.Camp;
import org.example.blood_donation_backend.repo.CampRepository;
import org.example.blood_donation_backend.service.CampService;
import org.example.blood_donation_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CampServiceImpl implements CampService {
    @Autowired
    private CampRepository campRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public int saveCamp(CampDTO campDTO) {
        if (campRepository.existsByEmail(campDTO.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            campRepository.save(modelMapper.map(campDTO, Camp.class));
            return VarList.Created;
        }
    }
}
