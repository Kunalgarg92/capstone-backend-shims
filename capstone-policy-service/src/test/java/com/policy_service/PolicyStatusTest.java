package com.policy_service;

import org.junit.jupiter.api.Test;

import com.policy_service.entity.PolicyStatus;

import static org.junit.jupiter.api.Assertions.*;

class PolicyStatusTest {

    @Test
    void policyStatus_enum_coverage() {
        PolicyStatus status = PolicyStatus.valueOf("SUSPENDED");
        assertEquals(PolicyStatus.SUSPENDED, status);

        assertEquals(3, PolicyStatus.values().length);
    }
}
