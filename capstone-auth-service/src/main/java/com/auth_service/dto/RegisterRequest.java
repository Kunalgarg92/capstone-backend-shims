package com.auth_service.dto;

public record RegisterRequest(
        String email,
        String password
) {}
