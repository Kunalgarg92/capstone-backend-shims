package com.policy_service.Controller;

import com.policy_service.dto.PolicyValidationResponse;
import com.policy_service.entity.Policy;
import com.policy_service.entity.PolicyStatus;
import com.policy_service.repository.PolicyRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internal/policies")
public class PolicyInternalController {

    private final PolicyRepository repository;

    public PolicyInternalController(PolicyRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{policyId}/validate/{userId}")
    public PolicyValidationResponse validatePolicy(
            @PathVariable Long policyId,
            @PathVariable Long userId
    ) {

        return repository.findByIdAndUserId(policyId, userId)
                .map(policy -> new PolicyValidationResponse(
                        policy.getStatus() == PolicyStatus.ACTIVE,
                        policy.getId(),
                        policy.getUserId(),
                        policy.getStatus().name()
                ))
                .orElse(new PolicyValidationResponse(
                        false,
                        policyId,
                        userId,
                        "NOT_OWNED"
                ));
    }
}
