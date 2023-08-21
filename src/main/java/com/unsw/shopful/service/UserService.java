package com.unsw.shopful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import com.unsw.shopful.repository.UserRepository;
import com.unsw.shopful.model.User;
import com.unsw.shopful.dto.UserDTO;
import com.unsw.shopful.mapper.UserMapper;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO findByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            return userMapper.toDto(user.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(String username, String email, String password) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);

        return userRepository.save(newUser);
    }
}
