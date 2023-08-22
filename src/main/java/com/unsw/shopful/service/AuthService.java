package com.unsw.shopful.service;

import java.util.HashSet;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unsw.shopful.model.User;
import com.unsw.shopful.ShopfulApplication;
import com.unsw.shopful.exception.BadRequestException;
import com.unsw.shopful.model.Role;
import com.unsw.shopful.repository.UserRepository;

@Service
public class AuthService {

    private static final Logger logger = ShopfulApplication.logger;
    
    @Autowired
    private UserRepository userRepository;

    public User register(String username, String email, String password) throws BadRequestException {

        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User()
            .setUsername(username)
            .setEmail(email)
            .setPassword(password)
            .setRoles(new HashSet<Role>());

        Role userRole = Role.ROLE_USER;

        user.getRoles().add(userRole);

        logger.info("Saving new user ... " + user);
        
        return userRepository.save(user);
    }
}
