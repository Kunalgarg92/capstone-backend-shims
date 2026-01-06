package com.policy_service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.policy_service.dto.CreateOrderRequest;
import com.policy_service.dto.PlanRequest;
import com.policy_service.dto.PolicyEnrollRequest;
import com.policy_service.dto.PolicyValidationResponse;
import com.policy_service.dto.VerifyPaymentRequest;

class DtoValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void createOrderRequest_valid() {
        CreateOrderRequest req = new CreateOrderRequest();
        req.setPolicyId(1L);
        req.setUserId(2L);
        req.setAmount(1000.0);

        Set<ConstraintViolation<CreateOrderRequest>> violations =
                validator.validate(req);

        assertTrue(violations.isEmpty());
    }

    @Test
    void createOrderRequest_invalid_amount() {
        CreateOrderRequest req = new CreateOrderRequest();
        req.setPolicyId(1L);
        req.setUserId(2L);
        req.setAmount(0.0);

        assertFalse(validator.validate(req).isEmpty());
    }

    @Test
    void planRequest_valid() {
        PlanRequest req = new PlanRequest();
        req.name = "Gold";
        req.coverageLimit = 100000.0;
        req.premiumAmount = 2000.0;
        req.durationYears = 2;

        assertTrue(validator.validate(req).isEmpty());
    }

    @Test
    void policyEnrollRequest_valid() {
        PolicyEnrollRequest req = new PolicyEnrollRequest();
        req.userId = 1L;
        req.planId = 2L;
        req.startDate = LocalDate.now();

        assertTrue(validator.validate(req).isEmpty());
    }

    @Test
    void verifyPaymentRequest_invalid() {
        VerifyPaymentRequest req = new VerifyPaymentRequest();

        assertFalse(validator.validate(req).isEmpty());
    }

    @Test
    void policyValidationResponse_constructor() {
        PolicyValidationResponse res =
                new PolicyValidationResponse(true, 1L, 2L, "ACTIVE");

        assertTrue(res.valid());
    }
}

