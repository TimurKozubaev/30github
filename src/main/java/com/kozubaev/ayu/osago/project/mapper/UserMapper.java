package com.kozubaev.ayu.osago.project.mapper;

import com.kozubaev.ayu.osago.project.dto.AuthRespose;
import com.kozubaev.ayu.osago.project.dto.user.UserDTO;
import com.kozubaev.ayu.osago.project.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    /**
     * Преобразует объект User в UserDTO.
     *
     * @param user объект User, который нужно преобразовать
     * @return объект UserDTO, заполненный данными из User
     */
    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPin(user.getPin());

        return userDTO;
    }

}