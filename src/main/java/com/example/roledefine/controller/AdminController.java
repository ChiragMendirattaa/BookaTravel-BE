package com.example.roledefine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String getAdminDashboard() {
        return "Hello Admin";
    }

}
