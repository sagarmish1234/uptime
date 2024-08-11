package com.uptime.dto.auth;

import com.uptime.model.UserInfo;

public record JwtResponse(String token, UserInfo userInfo) {
}
