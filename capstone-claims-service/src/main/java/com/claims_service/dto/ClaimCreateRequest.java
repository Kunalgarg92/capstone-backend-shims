package com.claims_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClaimCreateRequest(
        @NotNull Long policyId,
        @NotBlank String patientName,
        @NotNull Double treatmentCost,
        @NotBlank String documents
) {}

