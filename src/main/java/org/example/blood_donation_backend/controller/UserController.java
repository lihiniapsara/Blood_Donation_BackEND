package org.example.blood_donation_backend.controller;

import jakarta.validation.Valid;


import org.example.blood_donation_backend.QrGenerator.GenerateQr;
import org.example.blood_donation_backend.dto.AuthDTO;
import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.dto.UserDTO;
import org.example.blood_donation_backend.repo.UserRepository;
import org.example.blood_donation_backend.service.UserService;
import org.example.blood_donation_backend.util.JwtUtil;
import org.example.blood_donation_backend.util.VarList;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.blood_donation_backend.QrGenerator.GenerateQr.setData;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    //constructor injection
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("test");
        return "test";
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO) {
        System.out.println("register");

        try {
            /*// Assign default role "USER"
            Role defaultRole = UserRepository.findByRole("USER");
            userDTO.setRole(defaultRole);*/
            int res = userService.saveUser(userDTO);
            switch (res) {
                case VarList.Created -> {
                    String token = jwtUtil.generateToken(userDTO);


                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setEmail(userDTO.getEmail());
                    authDTO.setToken(token);


                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }

    }
    @PutMapping("/update-role")
    public ResponseEntity<ResponseDTO> updateUserRole(@RequestBody @Valid UserDTO userDTO) {
        try {
            int result = userService.updateUserRole(userDTO);

            if (result == VarList.Created) {
                return ResponseEntity.ok(new ResponseDTO(VarList.Created, "User role updated successfully", userDTO));
            } else if (result == VarList.Not_Found) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDTO(VarList.Not_Found, "User or Role not found", null));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(VarList.Bad_Request, "Invalid request", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Error updating role: " + e.getMessage(), null));
        }
    }


}
