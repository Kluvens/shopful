package com.unsw.shopful.security.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unsw.shopful.ShopfulApplication;
import com.unsw.shopful.exception.NotFoundException;
import com.unsw.shopful.model.User;
import com.unsw.shopful.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = ShopfulApplication.logger;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws NotFoundException {

        logger.info("Loading user by username ...");
        
        User user = userRepository.findByUsername(username);

        if (user == null) throw new NotFoundException("User not found by username: " + username);
    
        return UserDetailsImpl.build(user);
    }
}
