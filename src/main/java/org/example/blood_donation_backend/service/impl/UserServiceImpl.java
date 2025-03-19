package org.example.blood_donation_backend.service.impl;

import org.example.blood_donation_backend.dto.UserDTO;
import org.example.blood_donation_backend.entity.User;
import org.example.blood_donation_backend.repo.UserRepository;
import org.example.blood_donation_backend.service.UserService;
import org.example.blood_donation_backend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
    public int updateUserRole(UserDTO userDTO) {

        try {
            Optional<User> optionalUser = Optional.ofNullable(userRepository.findByRole(userDTO.getEmail()));

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                // Role එක check කරන්න
                if (userDTO.getRole() != null && userDTO.getRole() != null) {
                    Role newRole = (Role) userRepository.findByRole(userDTO.getRole());

                    if (newRole != null) {
                        user.setRole(String.valueOf(newRole));  // Role එක update කරන්න
                        userRepository.save(user);  // Database එකට save කරන්න
                        return VarList.Created;  // Updated = 200 (Success)
                    } else {
                        return VarList.Not_Found;  // Role එක හම්බුනේ නැත්නම්
                    }
                }
            }

            return VarList.Not_Found;  // User එක හම්බුනේ නැත්නම්
        } catch (Exception e) {
            e.printStackTrace();
            return VarList.Internal_Server_Error;  // Error එකක් ආවොත්
        }



    }


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


