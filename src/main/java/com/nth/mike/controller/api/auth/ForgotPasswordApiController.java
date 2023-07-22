package com.nth.mike.controller.api.auth;

import com.nth.mike.entity.Account;
import com.nth.mike.model.response.shared.TokenResponse;
import com.nth.mike.service.SendMailService;
import com.nth.mike.service.UserService;
import com.nth.mike.utils.HandleUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/mike/api/user/auth")
public class ForgotPasswordApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private HandleUserUtils handleUserUtils;

    @Value("${spring.createToken.resetCode.secret}")
    private String secretResetCode;

    @Value("${spring.createToken.username.secret}")
    private String secretUsername;
    @PostMapping("/forgot-password/generate-code")
    public ResponseEntity<?> generateCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Step 1: Validate email existence and user identity
        Account user = userService.findAccountByUserName(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid email address");
        }

        // Step 2: Generate and send reset code to the user's email
        String resetCode;
        do {
            resetCode = handleUserUtils.generateResetCode();
        } while (userService.findByResetCode(resetCode) != null);

        String finalResetCode = resetCode;
        CompletableFuture.runAsync(() -> {
            try {
                sendMailService.sendForgotPasswordCode(email, finalResetCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Store reset code and associated user information for validation
        userService.storeResetCode(email, resetCode);

        String encryptedEmail = handleUserUtils.createToken(email, secretUsername);
        return ResponseEntity.ok(new TokenResponse(encryptedEmail,"success"));
    }


    @PostMapping("/forgot-password/refresh-code")
    public ResponseEntity<String> refreshCode(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String email = handleUserUtils.getCodeFromToken(token, secretUsername);
        System.out.println(email);

        // Step 1: Validate email existence and user identity
        Account user = userService.findAccountByUserName(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid email address");
        }

        // Step 2: Generate and send reset code to the user's email
        String resetCode;
        do {
            resetCode = handleUserUtils.generateResetCode();
        } while (userService.findByResetCode(resetCode) != null);

        String finalResetCode = resetCode;
        CompletableFuture.runAsync(() -> {
            try {
                sendMailService.sendForgotPasswordCode(email, finalResetCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Store reset code and associated user information for validation
        userService.storeResetCode(email, resetCode);

        return ResponseEntity.ok("Reset code is created");
    }

    @PostMapping("/forgot-password/accept-code")
    public ResponseEntity<?> acceptResetCode(@RequestBody Map<String, String> request) {
        String resetCode = request.get("resetCode");
        // Step 3: Verify reset code
        if (!userService.isValidResetCode(resetCode)) {
            return ResponseEntity.badRequest().body("Invalid reset code");
        }

        // Step 4: Check reset code expiry
        if (userService.isResetCodeExpired(resetCode)) {
            return ResponseEntity.badRequest().body("Reset code has expired");
        }

        // Store the email and reset code in a secure location for the next step

        userService.removeCodeByExpired();

        Account account =userService.findByResetCode(resetCode);
        String acceptPassCode;
        do {
            acceptPassCode = handleUserUtils.generateResetCode();

            // Check if the reset code already exists for another user
        } while (userService.findByResetCode(acceptPassCode) != null);
        userService.storeAcceptPassCode(account.getUsername(),acceptPassCode);
        String encryptedEmail = handleUserUtils.createToken(acceptPassCode,secretResetCode);
        return ResponseEntity.ok(new TokenResponse(encryptedEmail,"success"));
    }

    @PostMapping("/forgot-password/reset-password")
    public ResponseEntity<String> acceptPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        String confirmPassword = request.get("confirmPassword");
        if (token==null) {
            return ResponseEntity.badRequest().body("Token not found");
        }
        String code=handleUserUtils.getCodeFromToken(token,secretResetCode);
        System.out.println(code);
        // Step 3: Verify reset code
        if (!userService.isValidResetCode(code)) {
            return ResponseEntity.badRequest().body("Invalid reset code");
        }

        // Step 4: Check reset code expiry
        if (userService.isResetCodeExpired(code)) {
            return ResponseEntity.badRequest().body("Reset code has expired");
        }

        // Retrieve the email and reset code from the secure location

        // Step 5: Validate and set a strong new password
        if (!handleUserUtils.isStrongPassword(newPassword)) {
            return ResponseEntity.badRequest().body("Password does not meet the requirements");
        }

        // Step 6: Confirm new password
        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        Account account =userService.findByResetCode(code);

        // Step 7: Update the user's password
        userService.changePassword(account.getUsername(), newPassword);

        // Step 8: Logging and monitoring
        handleUserUtils.logResetPasswordEvent(account.getUsername());

        return ResponseEntity.ok("Password reset successful");
    }


}
