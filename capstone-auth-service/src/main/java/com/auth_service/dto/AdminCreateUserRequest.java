package com.auth_service.dto;


import com.auth_service.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminCreateUserRequest(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotNull
        Role role
) {}
