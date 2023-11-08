package com.api.emailapi.controllers;

import com.api.emailapi.dto.ApiResponse;
import com.api.emailapi.dto.UserEntityDto;
import com.api.emailapi.exceptions.ExceptionCodes;
import com.api.emailapi.implementation.AuthImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {
    private final AuthImplementation authImplementation;
    @PostMapping
    public ResponseEntity<ApiResponse<String>> responseEntity(@RequestBody UserEntityDto userEntityDto) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        if (authImplementation.signUp(userEntityDto) == ExceptionCodes.ALREADY_EXISTS){
            apiResponse.setStatus(HttpStatus.CONFLICT);
            apiResponse.setData(" Email already exists");
        }  else if (authImplementation.signUp(userEntityDto) == ExceptionCodes.NOT_FORMATTED){
            apiResponse.setStatus(HttpStatus.FORBIDDEN);
            apiResponse.setData("Email not properly formatted");
        } else {
            apiResponse.setStatus(HttpStatus.OK);
            apiResponse.setData("Check your mail for confirmation token ");
        }

        //  System.out.println(userEntityDto);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    public record Confirm(String email, String token){ }

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<String>> confirmSignUp(@RequestBody Confirm confirmToken) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        if (authImplementation.confirmToken(confirmToken.email, confirmToken.token) == ExceptionCodes.SUCCESS) {
            apiResponse.setStatus(HttpStatus.OK);
            apiResponse.setData(" Successfully confirmed ");
        }  else {
            apiResponse.setStatus(HttpStatus.BAD_REQUEST);
            apiResponse.setData("confirmation token not correct ");
        }

        //  System.out.println(userEntityDto);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
}
