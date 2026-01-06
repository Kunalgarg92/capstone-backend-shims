package com.policy_service.dto;


public record PolicyValidationResponse(
        boolean valid,
        Long policyId,
        Long userId,
        String status
) {}


