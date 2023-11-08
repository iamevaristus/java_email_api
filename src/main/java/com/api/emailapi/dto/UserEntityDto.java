package com.api.emailapi.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.apache.catalina.mbeans.SparseUserDatabaseMBean;

import java.io.Serializable;

/**
 * DTO for {@link com.api.emailapi.entity.UserEntity}
 */
public record UserEntityDto(String email, String password) {
    public boolean validatePassword(){
        return password.length() > 5 && password.length() <= 25;
    }
 public boolean validateEmail(){
     return email.contains("@") && email.contains(".");
 }
}