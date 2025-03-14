package com.kozubaev.ayu.osago.project.dto;

import com.kozubaev.ayu.osago.project.dto.user.UserDTO;
import lombok.Data;

@Data
public class RegisterRequest {
    private String phoneNumber;
    private String password;
    private UserDTO userDTO;
}
