package com.example.roledefine.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

//    @GetMapping("/dashboard")
//    public String dashboard() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        for (GrantedAuthority auth : authentication.getAuthorities()) {
//            if ("ROLE_ADMIN".equals(auth.getAuthority())) {
//                return "Hello Admin";
//            }
//        }
//        return "Hello User";
//    }
@GetMapping("/admin/dashboard")
public String getAdminDashboard() {
    return "Hello Admin";
}

    @GetMapping("/user/dashboard")
    public String getUserDashboard() {
        return "Hello User";
    }
    @GetMapping("/user/demo")
    public String getDemo(){
    return "Demo";
    }
}
