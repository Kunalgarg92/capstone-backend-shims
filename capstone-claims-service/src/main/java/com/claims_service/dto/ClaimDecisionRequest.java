package com.claims_service.dto;

import jakarta.validation.constraints.NotBlank;

public record ClaimDecisionRequest(
        @NotBlank String remarks
) {}
