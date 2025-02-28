package com.kozubaev.ayu.osago.project.service;

import com.kozubaev.ayu.osago.project.config.JwtService;
import com.kozubaev.ayu.osago.project.dto.AuthRespose;
import com.kozubaev.ayu.osago.project.dto.AuthenticationRequest;
import com.kozubaev.ayu.osago.project.dto.RegisterRequest;
import com.kozubaev.ayu.osago.project.exeption.ResourceAlreadyExistsException;
import com.kozubaev.ayu.osago.project.exeption.UnauthorizedException;
import com.kozubaev.ayu.osago.project.mapper.UserMapper;
import com.kozubaev.ayu.osago.project.model.User;
import com.kozubaev.ayu.osago.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public AuthRespose register(RegisterRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResourceAlreadyExistsException("Username already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        // Save user to database
        User savedUser = userRepository.save(user);

        // Generate JWT token
        String token = jwtService.generateToken(savedUser);

        // Create response
        AuthRespose response = new AuthRespose();
        response.setUserDTO(userMapper.toDto(savedUser));
        response.setToken(token);

        return response;
    }

    public AuthRespose autheticate(AuthenticationRequest request) {
        try {
            // Authenticate user with Spring Security
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getPhoneNumber(),
                            request.getPassword()
                    )
            );

            // Find user by phone number
            User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                    .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

            // Generate JWT token
            String token = jwtService.generateToken(user);

            // Create response
            AuthRespose response = new AuthRespose();
            response.setUserDTO(userMapper.toDto(user));
            response.setToken(token);

            return response;
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Invalid credentials");
        }
    }
}