package com.kozubaev.ayu.osago.project.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String phoneNumber;
    private String password;
}
