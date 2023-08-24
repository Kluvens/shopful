package com.unsw.shopful.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.unsw.shopful.exception.NotFoundException;
import com.unsw.shopful.model.PasswordResetToken;
import com.unsw.shopful.model.User;
import com.unsw.shopful.request.CreateUserRequest;
import com.unsw.shopful.request.LoginRequest;
import com.unsw.shopful.request.PasswordResetAttemptRequest;
import com.unsw.shopful.request.PasswordResetRequest;
import com.unsw.shopful.security.jwt.JwtUtil;
import com.unsw.shopful.security.service.UserDetailsImpl;
import com.unsw.shopful.service.AuthService;
import com.unsw.shopful.service.EmailService;
import com.unsw.shopful.service.PasswordResetTokenService;
import com.unsw.shopful.service.UserService;
import com.unsw.shopful.utils.Utils;

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

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

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

    @PostMapping("/password-reset/request")
    public ResponseEntity<String> requestPasswordRest(@RequestBody PasswordResetAttemptRequest request) {
        User user = userService.findByEmail(request.getEmail());
        String token = Utils.generatePasswordResetToken();

        passwordResetTokenService.createPasswordResetToken(user.getId(), token);

        String resetLink = "http://localhost:3000/reset-password?token=" + token;
        // String emailContent = "Click the following link to reset your password: " + resetLink;
        // String subject = "Password reset";

        // emailService.sendEmail(user.getEmail(), subject, emailContent);

        return ResponseEntity.ok(resetLink);
    }

    @PostMapping("/password-reset/reset")
    public ResponseEntity<User> resetPassword(@RequestBody PasswordResetRequest request) {
        PasswordResetToken token = passwordResetTokenService.findbyToken(request.getToken());
        if (token != null && !token.isExpired()) {
            try {
                String newPassword = passwordEncoder.encode(request.getNewPassword());
                User user = userService.resetPassword(token.getUserId(), newPassword);
                return ResponseEntity.ok(user);
            } catch (NotFoundException e) {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
