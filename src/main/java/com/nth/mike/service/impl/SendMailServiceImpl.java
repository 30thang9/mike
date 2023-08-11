package com.nth.mike.service.impl;

import com.nth.mike.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendMailServiceImpl implements SendMailService {
    @Value("${spring.mail.username}")
    private String email;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendForgotPasswordCode(String toEmail, String resetCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(toEmail);
        message.setSubject("Reset Password");
        message.setText("Your reset code is: " + resetCode);
        mailSender.send(message);
        System.out.println("Email sent successfully!");
    }

    @Override
    public void sendVerifyEmail(String toEmail, String token) {
        try {
            // Tạo đối tượng MimeMessage
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Thiết lập thông tin email
            helper.setFrom(email);
            helper.setTo(toEmail);
            helper.setSubject("Email Verification");

            // Nội dung email là HTML
            String htmlContent = "<html><body><h1>Email Verification</h1><p>Please click the following link to verify your email:</p><p><a href='"
                    + "http://localhost:8080" + "/mike/auth/accept-email?token=" + token
                    + "'>Verify Email</a></p></body></html>";
            helper.setText(htmlContent, true); // Sử dụng true để đánh dấu là nội dung HTML

            // Gửi email
            mailSender.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
