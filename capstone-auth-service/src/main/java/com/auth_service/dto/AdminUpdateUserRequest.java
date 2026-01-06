package com.auth_service.dto;


import com.auth_service.entity.Role;

import jakarta.validation.constraints.NotNull;

public record AdminUpdateUserRequest(

        @NotNull
        Role role,

        boolean active
) {}
