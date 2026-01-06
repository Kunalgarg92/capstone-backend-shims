package com.claims_service.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.claims_service.dto.PolicyValidationResponse;

@FeignClient(name = "policy-service")
public interface PolicyClient {

    @GetMapping("/api/internal/policies/{policyId}/validate/{userId}")
    PolicyValidationResponse validatePolicy(
            @PathVariable Long policyId,
            @PathVariable Long userId
    );
}


