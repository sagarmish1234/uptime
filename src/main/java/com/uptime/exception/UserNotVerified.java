package com.uptime.exception;


public class UserNotVerified extends RuntimeException {
    public UserNotVerified() {
        super("User not verified");
    }
}
