package com.unsw.shopful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unsw.shopful.model.Product;
import com.unsw.shopful.model.User;
import com.unsw.shopful.service.ProductService;
import com.unsw.shopful.service.UserService;
import com.unsw.shopful.security.service.UserDetailsImpl;
import com.unsw.shopful.exception.NotFoundException;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public User getUserByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/profile")
    public User getUserDetails(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        String email = userDetailsImpl.getEmail();

        return userService.findByEmail(email);
    }

    @GetMapping("/all")
    // @Secured({"ROLE_ADMIN"})
    public List<User> getUsers(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return userService.getAllUsers();
    }

    @PostMapping("/{userId}/like")
    public ResponseEntity<User> likeProduct(@PathVariable String userId, @RequestParam String productId) {
        try {
            Product product = productService.findById(productId).get();
            User user = userService.likeProduct(userId, product);
            productService.incrementSavedCount(productId);
            return ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        
    }

    @PostMapping("/{userId}/unlike")
    public ResponseEntity<User> unlikeProduct(@PathVariable String userId, @RequestParam String productId) {
        try {
            Product product = productService.findById(productId).get();
            User user = userService.unlikeProduct(userId, product);
            productService.incrementSavedCount(productId);
            return ResponseEntity.ok(user);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // @PostMapping("/saveProduct")
    // public User saveProduct(@RequestParam String userId, @RequestParam String productId) {
    //     return userService.saveProduct(userId, productId);
    // }
}
