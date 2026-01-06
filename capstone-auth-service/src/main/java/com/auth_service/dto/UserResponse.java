package com.auth_service.dto;

import com.auth_service.entity.User;

public class UserResponse {

    public Long id;
    public String email;
    public String role;
    public boolean active;

    public UserResponse(Long id, String email, String role, boolean active) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.active = active;
    }
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                user.isActive()
        );
    }
}