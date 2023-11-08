package com.api.emailapi.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class ApiResponse<T> {
    private T data;
    private HttpStatus status;

}
