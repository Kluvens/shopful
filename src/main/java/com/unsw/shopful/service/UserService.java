package com.unsw.shopful.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import com.unsw.shopful.repository.UserRepository;
import com.unsw.shopful.model.Product;
import com.unsw.shopful.model.User;
import com.unsw.shopful.ShopfulApplication;
import com.unsw.shopful.dto.UserDTO;
import com.unsw.shopful.exception.NotFoundException;
import com.unsw.shopful.mapper.UserMapper;

@Service
public class UserService {

    private static final Logger logger = ShopfulApplication.logger;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public User findByEmail(String email) {

        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            // return userMapper.toDto(user.get());
            logger.info("found user");
            return user.get();
        }
        logger.error("user not found");
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
    }

    public User findByUserId(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            logger.info("found user");
            return user.get();
        }
        logger.error("user not found");
        throw new NotFoundException("User not found");
    }

    public User resetPassword(String userId, String newPassword) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User userEntity = user.get();
            userEntity.setPassword(newPassword);
            return userRepository.save(userEntity);
        }
        throw new NotFoundException("User not found");
    }

    public List<User> getAllUsers() {

        logger.info("Getting all users ...");
        
        return userRepository.findAll();
    }

    public User likeProduct(String userId, Product product) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            user.getLikedProducts().add(product);
            userRepository.save(user);

            return user;
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public User unlikeProduct(String userId, Product product) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            user.getLikedProducts().remove(product);
            userRepository.save(user);

            return user;
        } else {
            throw new NotFoundException("User not found");
        }
    }
}
