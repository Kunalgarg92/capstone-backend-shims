package com.policy_service.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PlanRequest {

    @NotBlank
    public String name;

    @NotNull @Min(10000)
    public Double coverageLimit;

    @NotNull @Min(500)
    public Double premiumAmount;

    @NotNull @Min(1)
    public Integer durationYears;
}

