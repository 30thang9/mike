package com.nth.mike.utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordEncoderUtils {
    private final PasswordEncoder passwordEncoder;

    public boolean isOldPasswordValid(String oldPassword, String oldPasswordRequest) {
        return passwordEncoder.matches(oldPasswordRequest, oldPassword);
    }
}
