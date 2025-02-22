package com.kozubaev.ayu.osago.project.controller;

import com.kozubaev.ayu.osago.project.dto.AuthRespose;
import com.kozubaev.ayu.osago.project.dto.AuthenticationRequest;
import com.kozubaev.ayu.osago.project.dto.RegisterRequest;
import com.kozubaev.ayu.osago.project.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "The Authentication API endpoints")
public class AuthController {
    private final AuthService authService;

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
                    schema = @Schema(implementation = AuthenticationRequest.class)
            )
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.autheticate(request));
    }
}