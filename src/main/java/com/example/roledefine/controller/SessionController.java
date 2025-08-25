package com.example.roledefine.controller;

import com.example.roledefine.entity.AnonymousUser;
import com.example.roledefine.repository.AnonymousUserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final AnonymousUserRepository anonymousUserRepository;

    @PostMapping("/start")
    public ResponseEntity<UUID> startAnonymousSession() {
        AnonymousUser anonymousUser = new AnonymousUser();
        UUID anonymousId = UUID.randomUUID();
        anonymousUser.setId(anonymousId);
        anonymousUserRepository.save(anonymousUser);

        ResponseCookie cookie = ResponseCookie.from("anonymous-id", anonymousId.toString())
                .path("/")
                .maxAge(60 * 60 * 24 * 30)
                .httpOnly(true)
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(anonymousId);
    }
}