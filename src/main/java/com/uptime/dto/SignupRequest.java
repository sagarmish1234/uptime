package com.uptime.dto;

public record SignupRequest(String email, String password, String firstName, String lastName, String company) {
}
