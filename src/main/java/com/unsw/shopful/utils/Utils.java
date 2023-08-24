package com.unsw.shopful.utils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    
    public static Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 5);
        return cal.getTime();
    }

    public static String generatePasswordResetToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}
