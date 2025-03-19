package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.UserDTO;

public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String userName);

    int updateUserRole(UserDTO userDTO);
}
