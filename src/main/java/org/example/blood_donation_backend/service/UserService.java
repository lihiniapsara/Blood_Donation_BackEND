package org.example.blood_donation_backend.service;

import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.dto.UserDTO;
import org.example.blood_donation_backend.entity.User;

public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String userName);

    public int updateUserRoleByEmail(UserDTO userDTO) ;
    ResponseDTO existsByUsername(User user);


}
