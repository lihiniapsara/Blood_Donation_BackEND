package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.dto.Blood_RequestDTO;
import org.example.blood_donation_backend.dto.CampDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.entity.Blood_Request;
import org.example.blood_donation_backend.entity.Camp;
import org.example.blood_donation_backend.entity.Hospital;
import org.example.blood_donation_backend.repo.Blood_RequestRepository;
import org.example.blood_donation_backend.repo.HospitalRepository;
import org.example.blood_donation_backend.service.Blood_RequestService;
import org.example.blood_donation_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class Blood_RequestServiceImpl implements Blood_RequestService {
    @Autowired
    private Blood_RequestRepository blood_requestRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private HospitalRepository hospitalRepository;

    public ResponseDTO getAll() {
        try {
            List<Blood_Request> bloodRequests = blood_requestRepository.findAll(); // Fetch all hospitals
            if (bloodRequests.isEmpty()) {
                return new ResponseDTO(VarList.No_Content, "No Blood Requests Found", null);
            }
            return new ResponseDTO(VarList.Created, "Blood Requests Retrieved Successfully", bloodRequests);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(VarList.Bad_Gateway, "An Unexpected Error Occurred", null);
        }
    }

    @Override
    public int save(Blood_RequestDTO blood_requestDTO) {
        System.out.println("Starting save with DTO: " + blood_requestDTO);

        // Fetch hospital
        Hospital hospital = hospitalRepository.findByHospitalName(blood_requestDTO.getHospitalName());
        System.out.println("Hospital fetched for name '" + blood_requestDTO.getHospitalName() + "': " + hospital);
        if (hospital == null) {
            System.out.println("Hospital not found in database: " + blood_requestDTO.getHospitalName());
            return VarList.Not_Acceptable; // Or a custom error code like VarList.Hospital_Not_Found
        }

        try {
            // Create Camp entity
            Blood_Request blood_request = new Blood_Request(
                    hospital,
                    blood_requestDTO.getFullName(),
                    blood_requestDTO.getEmail(),
                    blood_requestDTO.getPhoneNumber(),
                    blood_requestDTO.getBloodType(),
                    blood_requestDTO.getDistrict(),
                    blood_requestDTO.getMessage()

            );
            if (blood_requestDTO.getRequestDate() != null) {
                blood_request.setRequestDate(blood_requestDTO.getRequestDate()); // Ensure this is set
            } else {
                System.out.println("Request date is NULL in DTO!");
            }
            System.out.println("Blood Request created: " + blood_request);
            System.out.println("Hospital in Blood Request: " + blood_request.getHospital());

            // Save to database
            Blood_Request saveblood_request = blood_requestRepository.save(blood_request);
            System.out.println("Blood Request saved: " + saveblood_request);

            return VarList.Created;

        } catch (Exception e) {
            System.out.println("Exception during save: " + e.getMessage());
            e.printStackTrace();
            return VarList.Not_Acceptable;
        }
    }

    @Override
    public List<Blood_RequestDTO> getAllBloodRequest() {
        return blood_requestRepository.findAllBloodRequestsWithHospitalName();
    }



    /*@Override
    public register(ResponseDTO responseDTO) {
        try {
            Blood_Request blood_request = modelMapper.map(responseDTO, Blood_Request.class);
            blood_requestRepository.save(blood_request);
            return new ResponseDTO(VarList.Created, "Blood Request Registered Successfully", blood_request);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(VarList.Bad_Gateway, "An Unexpected Error Occurred", null);
        }
    }*/
}
