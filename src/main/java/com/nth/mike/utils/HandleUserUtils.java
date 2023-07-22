package com.nth.mike.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class HandleUserUtils {
    public String generateResetCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000; // Generate a random number from 100000 to 999999
        return String.valueOf(code);
    }

    public boolean isStrongPassword(String password) {
        // Add your password strength validation logic here
        // Return true if the password meets the requirements, false otherwise
        return true;
    }

    public void logResetPasswordEvent(String email) {
        // Add your password reset logging and monitoring logic here
        // Log the event or send notifications to relevant parties
    }

    public String createToken(String email, String secretKey) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String token = JWT.create()
                    .withSubject(email)
                    .sign(algorithm);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getCodeFromToken(String token, String secretKey) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);
            String email = decodedJWT.getSubject();
            return email;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
