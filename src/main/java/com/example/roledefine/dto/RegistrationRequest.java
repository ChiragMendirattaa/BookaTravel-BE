package com.example.roledefine.dto;

import com.example.roledefine.entity.Role;
import lombok.Data;

@Data
public class RegistrationRequest {
    private String username;
    private String password;
    private Role role;
}