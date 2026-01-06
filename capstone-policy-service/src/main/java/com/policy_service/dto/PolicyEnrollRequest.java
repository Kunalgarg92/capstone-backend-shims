package com.policy_service.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class PolicyEnrollRequest {

    @NotNull
    public Long userId;

    @NotNull
    public Long planId;

    @NotNull
    public LocalDate startDate;
}

