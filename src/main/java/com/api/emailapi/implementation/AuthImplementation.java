package com.api.emailapi.implementation;

import com.api.emailapi.dto.UserEntityDto;
import com.api.emailapi.entity.Token;
import com.api.emailapi.entity.UserEntity;
import com.api.emailapi.exceptions.ExceptionCodes;
import com.api.emailapi.repositories.TokenRepository;
import com.api.emailapi.repositories.UserRepository;
import com.api.emailapi.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class AuthImplementation implements AuthService {
    private final EmailImplementation emailImplementation;
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    @Override
    public int logIn(UserEntityDto userEntityDto) {
        AtomicInteger result = new AtomicInteger();
        if (userEntityDto.validateEmail()){
            repository.findEmail(userEntityDto.email()).ifPresentOrElse(userEntity -> {
                if (repository.checkIfEmailAndPasswordMatches(userEntityDto.email(), userEntityDto.password())) {
                    result.set(ExceptionCodes.SUCCESS);
                } else {
                    result.set(ExceptionCodes.DOES_NOT_MATCH);
                }
            }, () -> result.set(ExceptionCodes.DOES_NOT_EXIST));
        }  else {
            result.set(ExceptionCodes.NOT_FORMATTED);
        }
        return result.get();
    }

    @Override
    public int signUp(UserEntityDto userEntityDto) {
        AtomicInteger result = new AtomicInteger();
        if (userEntityDto.validateEmail() && userEntityDto.validatePassword()){
            repository.findEmail(userEntityDto.email()).ifPresentOrElse(
                    userEntity -> result.set(ExceptionCodes.ALREADY_EXISTS),
                    () -> {
                    String otp = emailImplementation.generateToken();
                    emailImplementation.sendEmail(userEntityDto.email(), otp);
                    UserEntity user = new UserEntity();
                    user.setEmail(userEntityDto.email());
                    user.setPassword(user.getPassword());
                    repository.save(user);

                    Token token = new Token();
                    token.setToken(otp);
                    token.setEmail(user.getEmail());
                    tokenRepository.save(token);


                    result.set(ExceptionCodes.SUCCESS);
                }
            );
        }  else {
            result.set(ExceptionCodes.NOT_FORMATTED);
        }
        return result.get();
    }

    @Override
    public int confirmToken(String email, String token) {
        AtomicInteger result = new AtomicInteger();
        tokenRepository.checkIfEmailAndTokenMatches(token,email).ifPresentOrElse(
                token1 -> {
                    token1.setExpired(true);
                    tokenRepository.save(token1);
                    result.set(ExceptionCodes.SUCCESS);
                },
                ()-> result.set(ExceptionCodes.DOES_NOT_MATCH));
        return result.get();
    }
}
