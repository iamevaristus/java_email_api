package com.api.emailapi.controllers;

import com.api.emailapi.dto.ApiResponse;
import com.api.emailapi.dto.UserEntityDto;
import com.api.emailapi.exceptions.ExceptionCodes;
import com.api.emailapi.implementation.AuthImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {
  private final AuthImplementation authImplementation;
  @PostMapping
  public ResponseEntity<ApiResponse<String>> responseEntity(@RequestBody UserEntityDto userEntityDto) {
   ApiResponse<String> apiResponse = new ApiResponse<>();
   if (authImplementation.logIn(userEntityDto) == ExceptionCodes.DOES_NOT_MATCH){
    apiResponse.setStatus(HttpStatus.CONFLICT);
    apiResponse.setData(" Incorrect email or password");
   } else if (authImplementation.logIn(userEntityDto) == ExceptionCodes.DOES_NOT_EXIST){
    apiResponse.setStatus(HttpStatus.BAD_REQUEST);
    apiResponse.setData("User does not exist");
   } else if (authImplementation.logIn(userEntityDto) == ExceptionCodes.NOT_FORMATTED){
       apiResponse.setStatus(HttpStatus.FORBIDDEN);
       apiResponse.setData("Email not properly formatted");
   } else {
    apiResponse.setStatus(HttpStatus.OK);
    apiResponse.setData("Welcome to emailApi");
   }

 //  System.out.println(userEntityDto);
   return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
  }
}
