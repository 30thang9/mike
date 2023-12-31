package com.nth.mike.controller.api.auth;

import com.nth.mike.constant.StatusConstant;
import com.nth.mike.entity.Account;
import com.nth.mike.entity.AccountStatus;
import com.nth.mike.entity.RoleName;
import com.nth.mike.model.dto.user.UserDTO;
import com.nth.mike.model.request.user.AddUserRequest;
import com.nth.mike.model.request.user.ChangePassRequest;
import com.nth.mike.model.response.shared.BasicResponse;
import com.nth.mike.model.response.shared.TokenResponse;
import com.nth.mike.service.SendMailService;
import com.nth.mike.service.UserService;
import com.nth.mike.utils.HandleUserUtils;
import com.nth.mike.utils.PasswordEncoderUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

@RestController
@RequestMapping("/mike/api/user")
public class UserApiController {
    @Autowired
    private UserService userService;

    @Autowired
    private HandleUserUtils handleUserUtils;

    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private PasswordEncoderUtils passwordEncoderUtils;

    @Value("${spring.createToken.resetCode.secret}")
    private String secretResetCode;

    @Value("${spring.createToken.username.secret}")
    private String secretUsername;

    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> getUser() {
        return ResponseEntity.ok(userService.findAllUser());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid AddUserRequest request) {
        String fullName = request.getFullName();
        String username = request.getUsername();
        String password = request.getPassword();
        String confirmPassword = request.getConfirmPassword();

        if (fullName.isBlank() || username.isBlank() || password.isBlank() || !password.equals(confirmPassword)
                || !isStrongPassword(password)) {
            return ResponseEntity.badRequest().body("Invalid input");
        }

        try {
            Account account = new Account();
            account.setFullName(fullName);
            account.setUsername(username);
            account.setPassword(password);
            userService.saveAccountHashPass(account);
            userService.addRoleToUser(account.getUsername(), RoleName.ROLE_USER);

            String resetCode;
            do {
                resetCode = handleUserUtils.generateResetCode();
            } while (userService.findByResetCode(resetCode) != null);

            String finalResetCode = resetCode;
            CompletableFuture.runAsync(() -> {
                try {
                    String finalCode = handleUserUtils.createToken(finalResetCode, secretResetCode);
                    sendMailService.sendVerifyEmail(account.getUsername(), finalCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            userService.storeResetCode(account.getUsername(), resetCode);

            String encryptedEmail = handleUserUtils.createToken(account.getUsername(), secretUsername);
            return ResponseEntity.ok(new TokenResponse(encryptedEmail, "success"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user");
        }
    }

    @PostMapping("/auth/verify-user/create-code")
    public ResponseEntity<?> createCodeVerifyUser(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        if (username.isBlank()) {
            return ResponseEntity.badRequest().body("Invalid input");
        }
        // Step 1: Validate email existence and user identity
        Account user = userService.findAccountByUserName(username);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid email address");
        }
        if (user.getAccountStatus() != AccountStatus.PENDING) {
            return ResponseEntity.badRequest().body("Account status is not pending ");
        }
        // Step 2: Generate and send reset code to the user's email
        try {
            String resetCode;
            do {
                resetCode = handleUserUtils.generateResetCode();
            } while (userService.findByResetCode(resetCode) != null);

            String finalResetCode = resetCode;
            CompletableFuture.runAsync(() -> {
                try {
                    String finalCode = handleUserUtils.createToken(finalResetCode, secretResetCode);
                    sendMailService.sendVerifyEmail(username, finalCode);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            userService.storeResetCode(username, resetCode);

            String encryptedEmail = handleUserUtils.createToken(username, secretUsername);
            return ResponseEntity.ok(new TokenResponse(encryptedEmail, "success"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to active user");
        }
    }

    @PostMapping("/auth/verify-user/refresh-code")
    public ResponseEntity<String> refreshCodeVerifyUser(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String email = handleUserUtils.getCodeFromToken(token, secretUsername);
        System.out.println(email);

        // Step 1: Validate email existence and user identity
        Account account = userService.findAccountByUserName(email);
        if (account == null) {
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
                String finalCode = handleUserUtils.createToken(finalResetCode, secretResetCode);
                sendMailService.sendVerifyEmail(email, finalCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        userService.storeResetCode(email, resetCode);

        return ResponseEntity.ok("Refresh token success");
    }

    @PostMapping("/auth/verify-user/accept-user")
    public ResponseEntity<String> acceptUser(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        if (token == null) {
            return ResponseEntity.badRequest().body("Token not found");
        }
        String code = handleUserUtils.getCodeFromToken(token, secretResetCode);
        System.out.println(code);
        // Step 3: Verify reset code
        if (!userService.isValidResetCode(code)) {
            return ResponseEntity.badRequest().body("Invalid reset code");
        }

        // Step 4: Check reset code expiry
        if (userService.isResetCodeExpired(code)) {
            return ResponseEntity.badRequest().body("Reset code has expired");
        }

        Account account = userService.findByResetCode(code);
        account.setAccountStatus(AccountStatus.ACTIVE);
        // Step 7: Update the user's
        userService.saveAccount(account);

        return ResponseEntity.ok("User verify successful");
    }

    @PostMapping("/change-pass")
    public ResponseEntity<?> changePass(@RequestBody @Valid ChangePassRequest request) {
        Account account = userService.findAccountByUserName(request.getUsername());
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BasicResponse(StatusConstant.ERROR, "Password and confirm password do not matches"));
        }
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BasicResponse(StatusConstant.ERROR, "Account not found"));
        }
        if (!passwordEncoderUtils.isOldPasswordValid(account.getPassword(), request.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BasicResponse(StatusConstant.ERROR, "Password is incorrect"));
        }
        try {
            userService.changePassword(request.getUsername(), request.getNewPassword());
            return ResponseEntity.ok("User change successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BasicResponse(StatusConstant.ERROR, "Change password failed"));
        }
    }

    private boolean isStrongPassword(String password) {
        // Add your password strength validation logic here
        // Return true if the password meets the requirements, false otherwise
        return true;
    }

}
