package com.user_management.dto;

import jakarta.validation.constraints.NotNull;

public record AdminUpdateUserRequest(

        @NotNull
        String role,

        @NotNull
        Boolean active
) {}

