package com.uptime.dto;

import com.uptime.model.UserInfo;

public record JwtResponse(String token, UserInfo userInfo) {
}
