package com.api.emailapi.implementation;

import com.api.emailapi.services.EmailService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailImplementation implements EmailService {
    @Autowired
    private JavaMailSender sender;
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    @Override
    public String generateToken() {
        return UUID.randomUUID().toString().substring(0, 5).toUpperCase();
    }

    @Override
    public void sendEmail(String email, String token) {
        mailMessage.setTo(email);
        mailMessage.setSubject("Confirm your Email");
        mailMessage.setText("Your OTP code is: %s. Please use this code to complete your verification.".formatted(token));
        mailMessage.setFrom("EmailApi");
        sender.send(mailMessage);
    }
}
