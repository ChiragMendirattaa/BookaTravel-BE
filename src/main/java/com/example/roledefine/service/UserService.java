package com.example.roledefine.service;

import com.example.roledefine.dto.RegistrationRequest;
import com.example.roledefine.entity.User;
import com.example.roledefine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User registerNewUser(RegistrationRequest registrationRequest) throws Exception {
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            throw new Exception("Username already exists");
        }
        User newUser = new User();
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        newUser.setRole(registrationRequest.getRole());
        return userRepository.save(newUser);
    }
}