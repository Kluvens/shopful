package com.unsw.shopful.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import com.unsw.shopful.repository.UserRepository;
import com.unsw.shopful.model.User;
import com.unsw.shopful.ShopfulApplication;
import com.unsw.shopful.dto.UserDTO;
import com.unsw.shopful.mapper.UserMapper;

@Service
public class UserService {

    private static final Logger logger = ShopfulApplication.logger;

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

        logger.info("Getting all users ...");
        
        return userRepository.findAll();
    }
}
