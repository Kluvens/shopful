package com.unsw.shopful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unsw.shopful.model.User;
import com.unsw.shopful.service.UserService;
import com.unsw.shopful.request.CreateUserRequest;
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

    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User creatUser(@RequestBody CreateUserRequest createUserRequest) {
        String username = createUserRequest.getUsername();
        String email = createUserRequest.getEmail();
        String password = createUserRequest.getPassword();

        return userService.createUser(username, email, password);
    }

    // @PostMapping("/saveProduct")
    // public User saveProduct(@RequestParam String userId, @RequestParam String productId) {
    //     return userService.saveProduct(userId, productId);
    // }
}
