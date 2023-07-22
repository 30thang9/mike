package com.nth.mike.service;

public interface SendMailService {
    void sendForgotPasswordCode(String email,String resetCode);

    void sendVerifyEmail(String toEmail, String resetCode);
}
