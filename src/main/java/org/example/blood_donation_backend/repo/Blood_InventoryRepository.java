package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.entity.Blood_Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface Blood_InventoryRepository extends JpaRepository<Blood_Inventory, String> {
    List<Blood_Inventory> findAll();

    boolean existsById(UUID id);

    void deleteById(String id);
}
