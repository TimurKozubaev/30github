package com.kozubaev.ayu.osago.project.controller;

import com.kozubaev.ayu.osago.project.config.dto.AuthRespose;
import com.kozubaev.ayu.osago.project.config.dto.AuthenticationRequest;
import com.kozubaev.ayu.osago.project.config.dto.RegisterRequest;
import com.kozubaev.ayu.osago.project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthRespose>register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthRespose>register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authService.autheticate(request));
    }
}
