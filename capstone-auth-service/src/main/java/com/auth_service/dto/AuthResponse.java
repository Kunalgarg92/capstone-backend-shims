package com.auth_service.dto;

public class AuthResponse {
    public String accessToken;
    public Long userId;
    public String role;

    public AuthResponse(String token, Long userId, String role) {
        this.accessToken = token;
        this.userId = userId;
        this.role = role;
    }
}
