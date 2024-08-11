package com.uptime.dto.auth;

public record SignupRequest(String email, String password, String firstName, String lastName, String company) {
}
