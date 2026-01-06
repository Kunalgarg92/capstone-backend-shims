package com.user_management.dto;

public record UserResponse(
        Long id,
        String email,
        String role,
        Boolean active
) {}

