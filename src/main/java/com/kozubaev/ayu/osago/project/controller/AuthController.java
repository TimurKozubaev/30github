package com.kozubaev.ayu.osago.project.controller;

import com.kozubaev.ayu.osago.project.dto.AuthRespose;
import com.kozubaev.ayu.osago.project.dto.LoginRequest;
import com.kozubaev.ayu.osago.project.dto.RegisterRequest;
import com.kozubaev.ayu.osago.project.service.AuthService;
import com.kozubaev.ayu.osago.project.service.otp.OtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "The Authentication API endpoints")
public class AuthController {
    private final AuthService authService;
    private final OtpService otpService;


    /**
     * Отправляет OTP на номер телефона.
     *
     * @param phoneNumber Номер телефона
     * @return Сообщение об успешной отправке
     */
    @PostMapping("/send")
    public String sendOtp(@RequestParam String phoneNumber) {
        otpService.sendOtp(phoneNumber);
        return "OTP отправлен на номер: " + phoneNumber;
    }

    /**
     * Проверяет введенный OTP.
     *
     * @param phoneNumber Номер телефона
     * @param userOtp     OTP, введенный пользователем
     * @return Сообщение о результате проверки
     */
    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String phoneNumber, @RequestParam String userOtp) {
        boolean isOtpValid = otpService.verifyOtp(phoneNumber, userOtp);
        if (isOtpValid) {
            return "OTP успешно подтвержден.";
        } else {
            return "Неверный OTP. Попробуйте снова.";
        }
    }




    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account with the provided registration details"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully registered",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthRespose.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid registration details provided",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User already exists",
                    content = @Content
            )
    })
    @PostMapping("/register")
    public ResponseEntity<AuthRespose> register(
            @Parameter(
                    description = "Registration details",
                    required = true,
                    schema = @Schema(implementation = RegisterRequest.class)
            )
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user and returns a JWT token"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully authenticated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthRespose.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Account locked or disabled",
                    content = @Content
            )
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthRespose> authenticate(
            @Parameter(
                    description = "Authentication credentials",
                    required = true,
                    schema = @Schema(implementation = LoginRequest.class)
            )
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}