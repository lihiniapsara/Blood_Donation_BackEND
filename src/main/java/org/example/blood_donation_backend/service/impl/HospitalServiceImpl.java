package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.dto.HospitalDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.dto.UserDTO;
import org.example.blood_donation_backend.entity.Blood_Bank;
import org.example.blood_donation_backend.entity.Donor;
import org.example.blood_donation_backend.entity.Hospital;
import org.example.blood_donation_backend.entity.User;
import org.example.blood_donation_backend.repo.Blood_BankRepository;
import org.example.blood_donation_backend.repo.HospitalRepository;
import org.example.blood_donation_backend.repo.UserRepository;
import org.example.blood_donation_backend.service.HospitalService;
import org.example.blood_donation_backend.service.UserService;
import org.example.blood_donation_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Blood_BankRepository blood_bankRepository;
@Override
    public List<HospitalDTO> getHospitalsByDistrict(String district) {
        List<Hospital> hospitals = hospitalRepository.findByDistrict(district);
        return hospitals.stream().map(hospital -> {
            HospitalDTO dto = new HospitalDTO();
            dto.setHospitalName(hospital.getHospitalName());
            return dto;
        }).collect(Collectors.toList());
    }
    @Override
    public int saveHospital(HospitalDTO hospitalDTO) {
        /*try {
            User user=new User();
            userRepository.findByUsername(userDTO.getUserName());
            hospitalRepository.save(user);
        } catch (Exception e) {
            return VarList.Not_Found;
        }*/

        System.out.println("jjjjjjj");
        /*if (hospitalRepository.existsByOfficialEmail(hospitalDTO.getOfficialEmail())) {
            return VarList.Not_Acceptable;
        }else {*/
            //hospitalRepository.save(modelMapper.map(hospitalDTO, Hospital.class));

            try {
                System.out.println("into tryyyyyy");
               Hospital hospital = new Hospital(
                       userRepository.findByUsername(hospitalDTO.getUserName()),
                        hospitalDTO.getHospitalName(),
                        hospitalDTO.getTypeOfHospital(),
                        hospitalDTO.getRegistrationNumber(),
                        hospitalDTO.getYearOfEstablishment(),
                        hospitalDTO.getAddress(),
                        hospitalDTO.getCity(),
                        hospitalDTO.getDistrict(),
                        hospitalDTO.getProvince(),
                        hospitalDTO.getZipCode(),
                        hospitalDTO.getOfficialEmail(),
                        hospitalDTO.getContactNumber(),
                        hospitalDTO.getEmergencyContactNumber(),
                        hospitalDTO.getWebsite(),
                        hospitalDTO.isHasBloodBank(),
                        hospitalDTO.getBloodBankContactPersonName(),
                        hospitalDTO.getBloodBankContactNumber(),
                        hospitalDTO.getAvailableBloodGroups(),
                        hospitalDTO.getBloodBankLicenseNumber(),
                        hospitalDTO.getHealthMinistryApprovalCertificate(),
                        hospitalDTO.isEmergencyBloodServiceAvailable(),
                        hospitalDTO.isBloodDonationCampFacility(),

                        /*hospitalDTO.getEmergencyBloodServiceAvailable(),
                        hospitalDTO.getBloodDonationCampFacility(),*/
                        hospitalDTO.getNumberOfBloodStorageUnits()
               );
                System.out.println(hospital+"cvbnm");


/*
                       userRepository.findByUsername(hospitalDTO.getUserName());
*/
               hospitalRepository.save(hospital);
               User user = userRepository.findByUsername(hospitalDTO.getUserName());
               user.setRole("Admin");
               userRepository.save(user);
               return VarList.Created;

            }catch (Exception e) {
                e.printStackTrace();
                return VarList.Not_Found;
            }

        }
  //  }


    @Override
    public ResponseDTO getAllHospitals() {
        try {
            List<Hospital> hospitalList = hospitalRepository.findAll(); // Fetch all hospitals

            if (hospitalList.isEmpty()) {
                return new ResponseDTO(VarList.No_Content, "No Hospitals Found", null);
            }

            return new ResponseDTO(VarList.Created, "Hospitals Retrieved Successfully", hospitalList);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(VarList.Bad_Gateway, "An Unexpected Error Occurred", null);
        }
    }


}
