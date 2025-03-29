package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.dto.ResponseDTO;
import org.example.blood_donation_backend.dto.UserDTO;
import org.example.blood_donation_backend.entity.User;
import org.example.blood_donation_backend.repo.UserRepository;
import org.example.blood_donation_backend.service.UserService;
import org.example.blood_donation_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
    @Transactional
    public class UserServiceImpl implements UserDetailsService, UserService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ModelMapper modelMapper;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            User user = userRepository.findByEmail(email);
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
        }



        public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByEmail(username);
            return modelMapper.map(user,UserDTO.class);
        }

        private Set<SimpleGrantedAuthority> getAuthority(User user) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            return authorities;
        }

        @Override
        public UserDTO searchUser(String username) {
            if (userRepository.existsByEmail(username)) {
                User user=userRepository.findByEmail(username);
                return modelMapper.map(user,UserDTO.class);
            } else {
                return null;
            }
        }

    @Override
    public int updateUserRoleByEmail(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail());

        if (user != null && userDTO.getRole() != null) {
            user.setRole(userDTO.getRole());
            userRepository.save(user);
            return VarList.Created;
        } else {
            return VarList.Not_Found;
        }
    }

    public ResponseDTO existsByUsername(User user) {
        /*if (userRepository.existsByUsername(user.getUsername())) {
            return new ResponseDTO(VarList.Not_Acceptable, "Username is already taken", null);
        }*/
        userRepository.save(user);
        return new ResponseDTO(VarList.Created, "User registered successfully", user);
    }


   /* public UserDTO findByUsername(String username) {
        try {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                return new ResponseDTO(VarList.No_Content, "No Users Found", null);
            }
            return new ResponseDTO(VarList.Created, "Users Retrieved Successfully", user);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(VarList.Bad_Gateway, "An Unexpected Error Occurred", null);
        }
    }*/

    @Override
        public int saveUser(UserDTO userDTO) {
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                return VarList.Not_Acceptable;
            } else {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                userRepository.save(modelMapper.map(userDTO, User.class));
                return VarList.Created;
            }
        }

        public boolean ifEmailExists(String email) {
            return userRepository.existsByEmail(email);
        }
    }


