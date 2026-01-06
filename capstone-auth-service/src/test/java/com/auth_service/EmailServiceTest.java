package com.auth_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.auth_service.service.EmailService;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender sender;

    @InjectMocks
    private EmailService service;

    @Test
    void sendUserCreationEmail_success() {
        service.sendUserCreationEmail("a@test.com", "ADMIN", "temp123");
        verify(sender).send(any(SimpleMailMessage.class));
    }
}

