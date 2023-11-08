package com.api.emailapi.services;

import com.api.emailapi.dto.UserEntityDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface AuthService {
    int logIn(UserEntityDto userEntityDto);
    int signUp(UserEntityDto userEntityDto);
    int confirmToken(String email, String token);
}
