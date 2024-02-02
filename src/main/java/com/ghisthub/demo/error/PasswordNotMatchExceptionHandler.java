package com.ghisthub.demo.error;

import com.ghisthub.demo.Model.Response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class PasswordNotMatchExceptionHandler extends ResponseEntityExceptionHandler  {
    @ExceptionHandler(PasswordDoesNotMatchException.class)
    public ResponseEntity<ErrorResponse> passwordNotMatch(PasswordDoesNotMatchException exception) {
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(NOT_FOUND, false, exception.getMessage()));
    }
}
