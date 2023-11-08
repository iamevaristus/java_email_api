package com.api.emailapi.services;

import jakarta.annotation.Nullable;
import lombok.Builder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    String generateToken();
    void sendEmail(String email, String token);
}
