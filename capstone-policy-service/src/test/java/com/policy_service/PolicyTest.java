package com.policy_service;

import org.junit.jupiter.api.Test;

import com.policy_service.entity.InsurancePlan;
import com.policy_service.entity.Policy;
import com.policy_service.entity.PolicyStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PolicyTest {

    @Test
    void policy_getters_setters() {
        InsurancePlan plan = new InsurancePlan();
        plan.setId(1L);
        plan.setName("Gold Plan");

        Policy policy = new Policy();
        policy.setId(100L);
        policy.setUserId(55L);
        policy.setPlan(plan);
        policy.setStartDate(LocalDate.now());
        policy.setEndDate(LocalDate.now().plusYears(1));
        policy.setStatus(PolicyStatus.ACTIVE);

        assertEquals(100L, policy.getId());
        assertEquals(55L, policy.getUserId());
        assertEquals(plan, policy.getPlan());
        assertEquals(PolicyStatus.ACTIVE, policy.getStatus());
        assertNotNull(policy.getStartDate());
        assertNotNull(policy.getEndDate());
    }
}

