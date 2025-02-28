package com.kozubaev.ayu.osago.project.dto;

import com.kozubaev.ayu.osago.project.dto.user.UserDTO;
import lombok.Data;

@Data
public class AuthRespose {
    private UserDTO userDTO;
    private String token;

}
