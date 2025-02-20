package com.kozubaev.ayu.osago.project.service;

import com.kozubaev.ayu.osago.project.dto.AuthRespose;
import com.kozubaev.ayu.osago.project.dto.AuthenticationRequest;
import com.kozubaev.ayu.osago.project.dto.RegisterRequest;
import com.kozubaev.ayu.osago.project.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
    }

    public AuthRespose register(RegisterRequest request) {return null;    }
    public AuthRespose autheticate(AuthenticationRequest request) {
        return null;
    }


}
