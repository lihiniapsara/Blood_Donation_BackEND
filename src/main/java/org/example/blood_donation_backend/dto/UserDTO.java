package org.example.blood_donation_backend.dto;

import com.fasterxml.jackson.databind.BeanProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private String role;
    private String fullName; // Added
    private String mobileNumber; // Added
    private String nicNumber; // Added

    public UserDTO( String email, String password) {
        this.email = email;
        this.password = password;
    }

}
