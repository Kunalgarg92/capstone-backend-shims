package com.policy_service;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import com.policy_service.entity.InsurancePlan;

import static org.junit.jupiter.api.Assertions.*;

class InsurancePlanTest {

    @Test
    void insurancePlan_getters_setters_and_validation() {
        InsurancePlan plan = new InsurancePlan();

        plan.setId(1L);
        plan.setName("Health Plus");
        plan.setCoverageLimit(100000.0);
        plan.setPremiumAmount(1200.0);
        plan.setDurationYears(2);

        assertEquals(1L, plan.getId());
        assertEquals("Health Plus", plan.getName());
        assertEquals(100000.0, plan.getCoverageLimit());
        assertEquals(1200.0, plan.getPremiumAmount());
        assertEquals(2, plan.getDurationYears());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        assertTrue(validator.validate(plan).isEmpty());
    }
}
