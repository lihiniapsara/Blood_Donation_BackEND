package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.dto.DonorDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.entity.Donor;
import org.example.blood_donation_backend.repo.DonorRepository;
import org.example.blood_donation_backend.service.DonorService;
import org.example.blood_donation_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            System.out.println(donorDTO+"1");
            return VarList.Not_Acceptable;
        }else {
            donorRepository.save(modelMapper.map(donorDTO, Donor.class));
            System.out.println(donorDTO+"2");
            return VarList.Created;
        }
    }

    @Override
    public ResponseDTO getAllDonors() {

        try {
            List<Donor> donorList = donorRepository.findAll(); // Fetch all donors
            if (donorList.isEmpty()) {
                return new ResponseDTO(VarList.No_Content, "No Donors Found", null);
            }

            return new ResponseDTO(VarList.Created, "Donors Retrieved Successfully", donorList);
        } catch (Exception e) {
            return new ResponseDTO(VarList.Bad_Gateway, "An Unexpected Error Occurred", null);// >
        }
    }


    @Override
    public ResponseDTO updateDonor(DonorDTO donorDTO) {
        Donor donor = (Donor) donorRepository.findByContact(donorDTO.getContact()).orElse(null);

        if (donor != null) {
            donor.setFullName(donorDTO.getFullName());
            donor.setDateOfBirth(donorDTO.getDateOfBirth());
            donor.setGender(donorDTO.getGender());
            donor.setBloodGroup(donorDTO.getBloodGroup());
            donor.setNicOrPassport(donorDTO.getNicOrPassport());
            donor.setContact(donorDTO.getContact());
            donor.setEmail(donorDTO.getEmail());
            donor.setAddress(donorDTO.getAddress());
            donor.setCity(donorDTO.getCity());
            donor.setDistrict(donorDTO.getDistrict());
            donor.setProvince(donorDTO.getProvince());
            donor.setZipCode(donorDTO.getZipCode());

            donorRepository.save(donor); // Update the donor

            return new ResponseDTO(VarList.Created, "Donor Updated Successfully", null);
        } else {
            return new ResponseDTO(VarList.Not_Found, "Donor Not Found", null);
        }
    }
    @Override
    public List<Donor> findByDistrictAndBloodGroupIn(String district, List<String> bloodTypes) {
        return donorRepository.findByDistrictAndBloodGroupIn(district, bloodTypes);
    }

   /* @Override
    public List<DonorDTO> getDonorsByDistrict(String district) {
        List<Donor> donors = donorRepository.findByDistrict(district);
        return donors.stream().map(donor -> {
            DonorDTO dto = new DonorDTO();
            dto.setEmail(donor.getEmail());
            return dto;
        }).collect(Collectors.toList());
    }*/
}
/*
if (user != null && userDTO.getRole() != null) {
        user.setRole(userDTO.getRole());
        userRepository.save(user);
            return VarList.Created;
        } else {
                return VarList.Not_Found;
        }*/
