package com.unsw.shopful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unsw.shopful.model.Role;
import com.unsw.shopful.model.User;
import com.unsw.shopful.service.UserService;
import com.unsw.shopful.request.CreateUserRequest;
import com.unsw.shopful.security.service.UserDetailsImpl;
import com.unsw.shopful.dto.UserDTO;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public UserDTO getUserByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/profile")
    public UserDTO getUserDetails(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        String email = userDetailsImpl.getEmail();

        return userService.findByEmail(email);
    }

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN"})
    public List<User> getUsers(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return userService.getAllUsers();
    }

    // @PostMapping("/saveProduct")
    // public User saveProduct(@RequestParam String userId, @RequestParam String productId) {
    //     return userService.saveProduct(userId, productId);
    // }
}
