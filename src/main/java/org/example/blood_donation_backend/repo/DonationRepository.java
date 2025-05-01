package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.entity.Donation;
import org.example.blood_donation_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation,String> {
    List<Donation> findByUser(User user);

    boolean existsByTransactionId(String transactionId);}
