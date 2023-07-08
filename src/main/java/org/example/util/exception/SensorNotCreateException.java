package org.example.util.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.util.ErrorResponse;

@AllArgsConstructor
@Data
public class SensorNotCreateException extends Exception{
    private ErrorResponse errorResponse;
}
