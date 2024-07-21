package com.uptime.advice;

import com.uptime.dto.MessageResponse;
import com.uptime.exception.UserExistsException;
import com.uptime.exception.UserNotVerified;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Order(0)
    @ExceptionHandler(UserNotVerified.class)
    public ResponseEntity<?> handleUserNotVerified(UserNotVerified ex, WebRequest ignored){
        log.error("User is not verified");
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @Order(0)
    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<?> handleUserExistsException(UserExistsException ex, WebRequest ignored){
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.CONFLICT);
    }
    @Order(0)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex, WebRequest ignored){
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @Order(1)
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleException(Throwable ex, WebRequest ignored){
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
