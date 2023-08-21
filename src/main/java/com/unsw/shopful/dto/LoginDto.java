package com.unsw.shopful.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class LoginDto {
    
    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;
}
