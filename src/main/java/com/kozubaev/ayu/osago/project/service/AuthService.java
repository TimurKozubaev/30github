package com.kozubaev.ayu.osago.project.service;

import com.kozubaev.ayu.osago.project.config.JwtService;
import com.kozubaev.ayu.osago.project.dto.AuthRespose;
import com.kozubaev.ayu.osago.project.dto.LoginRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public AuthRespose register(RegisterRequest request) {
        // Проверка, существует ли пользователь с таким номером телефона
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ResourceAlreadyExistsException("Phone number already exists");
        }

        // Создание нового пользователя
        User user = new User();
        user.setUsername(request.getPhoneNumber()); // Используем номер телефона как username
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getUserDTO().getEmail());
        user.setPin(request.getUserDTO().getPin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Сохранение пользователя в базе данных
        User savedUser = userRepository.save(user);

        // Генерация JWT токена
        String token = jwtService.generateToken(savedUser);

        // Создание ответа
        AuthRespose response = new AuthRespose();
        response.setUserDTO(userMapper.toDto(savedUser));
        response.setToken(token);

        return response;
    }

    // Логин уже реализован в вашем коде
    public AuthRespose login(LoginRequest request) {
        try {
            // Аутентификация пользователя с помощью Spring Security
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getPhoneNumber(),
                            request.getPassword()
                    )
            );

            // Поиск пользователя по номеру телефона
            User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                    .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

            // Генерация JWT токена
            String token = jwtService.generateToken(user);

            // Создание ответа
            AuthRespose response = new AuthRespose();
            response.setUserDTO(userMapper.toDto(user));
            response.setToken(token);

            return response;
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Invalid credentials");
        }
    }
}