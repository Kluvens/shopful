package com.unsw.shopful.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Document(collection = "password_reset_tokens")
public class PasswordResetToken {
    
    @Id
    private String id;

    private String token;
    private String userId;
    private Date expiryDate;

    public Boolean isExpired() {
        return new Date().after(this.expiryDate);
    }
}
