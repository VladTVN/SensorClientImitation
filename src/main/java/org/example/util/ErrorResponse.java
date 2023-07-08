package org.example.util;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
public class ErrorResponse {
    private String message;
    private long timestamp;
    private HttpStatusCode httpStatusCode;
}