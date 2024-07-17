package com.uptime.advice;

import com.uptime.dto.MessageResponse;
import com.uptime.exception.UserExistsException;
import com.uptime.exception.UserNotVerified;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotVerified.class)
    public ResponseEntity<?> handleUserNotVerified(UserNotVerified ex, WebRequest ignored){
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<?> handleUserExistsException(UserExistsException ex, WebRequest ignored){
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.CONFLICT);
    }

}
