package com.uptime.exception;

public class UserExistsException extends RuntimeException {

    public UserExistsException() {
        super("User already exits");
    }

}
