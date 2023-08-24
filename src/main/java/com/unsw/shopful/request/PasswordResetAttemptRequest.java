package com.unsw.shopful.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetAttemptRequest {
    
    private String email;
}
