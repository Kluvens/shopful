package com.unsw.shopful.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unsw.shopful.ShopfulApplication;
import com.unsw.shopful.dto.LoginDto;
import com.unsw.shopful.exception.BadRequestException;
import com.unsw.shopful.request.CreateUserRequest;
import com.unsw.shopful.request.LoginRequest;
import com.unsw.shopful.security.jwt.JwtUtil;
import com.unsw.shopful.security.service.UserDetailsImpl;
import com.unsw.shopful.service.AuthService;

@RestController
@RequestMapping("/api/authentication")
public class AuthController {

    private static final Logger logger = ShopfulApplication.logger;
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CreateUserRequest createUserRequest) {

        String username = createUserRequest.getUsername();
        String email = createUserRequest.getEmail();
        String password = passwordEncoder.encode(createUserRequest.getPassword());
        logger.info("Password: " + password);

        try {
            authService.register(username, email, password);
            return ResponseEntity.ok("Successfully created a new user");
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        logger.info("Logging in ...");

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtil.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(
            new LoginDto()
            .setToken(jwt)
            .setId(userDetails.getId())
            .setUsername(userDetails.getUsername())
            .setEmail(userDetails.getEmail())
        );
    }
}
