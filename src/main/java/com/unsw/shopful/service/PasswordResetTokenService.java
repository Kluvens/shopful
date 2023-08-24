package com.unsw.shopful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unsw.shopful.model.PasswordResetToken;
import com.unsw.shopful.repository.PasswordResetTokenRepository;
import com.unsw.shopful.utils.Utils;

@Service
public class PasswordResetTokenService {
    
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetToken findbyToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    public PasswordResetToken createPasswordResetToken(String userId, String token) {
        PasswordResetToken resetToken = new PasswordResetToken()
            .setUserId(userId)
            .setToken(token)
            .setExpiryDate(Utils.calculateExpiryDate());

        return passwordResetTokenRepository.save(resetToken);
    }
}
