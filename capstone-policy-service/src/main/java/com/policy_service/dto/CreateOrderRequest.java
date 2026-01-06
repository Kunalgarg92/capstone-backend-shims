package com.policy_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderRequest {

    @NotNull
    private Long policyId;

    @NotNull
    private Long userId;

    @NotNull
    @Min(1)
    private Double amount;
}
