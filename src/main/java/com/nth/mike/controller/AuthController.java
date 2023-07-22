package com.nth.mike.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mike/auth")
public class AuthController {
    @GetMapping("/login")
    public String login() {
        return "views/auth/login";
    }

    @GetMapping("/register")
    public String register() {
        return "views/auth/register";
    }
    @GetMapping("/forgot-password-step1")
    public String forgotPasswordStep1() {
        return "views/auth/forgot-password-step1";
    }
    @GetMapping("/forgot-password-step2")
    public String forgotPasswordStep2() {
        return "views/auth/forgot-password-step2";
    }
    @GetMapping("/forgot-password-step3")
    public String forgotPasswordStep3() {
        return "views/auth/forgot-password-step3";
    }
    @GetMapping("/reset-password")
    public String resetPassword() {
        return "views/auth/reset-password";
    }
    @GetMapping("/verify-email")
    public String verifyEmail() {
        return "views/auth/verify-email";
    }

    @GetMapping("/accept-email")
    public String acceptEmail() {
        return "views/auth/accept-email";
    }
}
