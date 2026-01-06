package com.auth_service.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendUserCreationEmail(String to, String role, String tempPassword) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Account Has Been Created");

        message.setText("""
                Hello,

                Your account has been successfully created.

                Role: %s
                Temporary Password: %s

                Please login and change your password immediately for security reasons.

                Login URL: http://localhost:4200/login

                Regards,
                Admin Team
                """.formatted(role, tempPassword));

        mailSender.send(message);
    }
}

