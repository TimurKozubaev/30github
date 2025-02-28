package com.kozubaev.ayu.osago.project.mapper;

import com.kozubaev.ayu.osago.project.dto.user.UserDTO;
import com.kozubaev.ayu.osago.project.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setEmail(user.getEmail());
        userDTO.setPin(user.getPin());
        // Don't set password in DTO for security reasons

        return userDTO;
    }

    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        return new User(
                userDTO.getUsername(),
                userDTO.getPhoneNumber(),
                userDTO.getEmail(),
                userDTO.getPin(),
                userDTO.getPassword()
        );
    }
}