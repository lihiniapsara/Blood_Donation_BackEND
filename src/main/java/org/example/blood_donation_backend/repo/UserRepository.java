package org.example.blood_donation_backend.repo;

import org.example.blood_donation_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User,String> {

    User findByEmail(String userName);

    boolean existsByEmail(String userName);

    int deleteByEmail(String userName);

    User findByRole(String role);

    boolean existsByUsername(String username);


    User findByUsername(String username);
    @Modifying
    @Query(value = "UPDATE systemuser u SET u.email = ?2 WHERE u.first_name = ?1", nativeQuery = true)
    void updatepassword(String email, String password);

}