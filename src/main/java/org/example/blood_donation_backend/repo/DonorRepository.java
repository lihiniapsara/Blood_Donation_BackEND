package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.dto.DonorDTO;
import org.example.blood_donation_backend.entity.Donor;
import org.example.blood_donation_backend.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor, String> {
    boolean existsByEmail(String email);
    List<Donor> findByDistrictAndBloodGroupIn(String district, List<String> bloodGroups);
    Optional<Object> findByContact(String contact);
    List<Donor> findByDistrict(String district);

    @Modifying
    @Query("UPDATE Donor d SET d.fullName = :fullName, d.dateOfBirth = :dateOfBirth, d.gender = :gender, d.bloodGroup = :bloodGroup, d.nicOrPassport = :nicOrPassport, d.contact = :contact, d.email = :email, d.Address = :address, d.city = :city, d.district = :district, d.province = :province, d.zipCode = :zipCode WHERE d.id = :id")
    void updateDonor(@Param("fullName") String fullName,
                     @Param("dateOfBirth") String dateOfBirth,
                     @Param("gender") String gender,
                     @Param("bloodGroup") String bloodGroup,
                     @Param("nicOrPassport") String nicOrPassport,
                     @Param("contact") String contact,
                     @Param("email") String email,
                     @Param("address") String address,
                     @Param("city") String city,
                     @Param("district") String district,
                     @Param("province") String province,
                     @Param("zipCode") String zipCode);

}
