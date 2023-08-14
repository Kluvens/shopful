package com.unsw.shopful.mapper;

import org.springframework.stereotype.Component;

import com.unsw.shopful.dto.UserDTO;
import com.unsw.shopful.model.User;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        UserDTO dto = new UserDTO()
            .setEmail(user.getEmail())
            .setUsername(user.getUsername())
            .setPassword(user.getPassword());

        return dto;
    }
}
