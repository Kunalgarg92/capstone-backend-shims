package com.user_management.dto;

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
        String role  
) {}
