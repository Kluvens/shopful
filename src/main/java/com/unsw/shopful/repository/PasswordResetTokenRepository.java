package com.unsw.shopful.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.unsw.shopful.model.PasswordResetToken;

public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {
    
    public PasswordResetToken findByToken(String token);
}
