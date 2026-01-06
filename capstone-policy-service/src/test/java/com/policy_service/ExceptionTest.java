package com.policy_service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.policy_service.exception.BadRequestException;
import com.policy_service.exception.PolicyOperationException;
import com.policy_service.exception.ResourceNotFoundException;

class ExceptionTest {

    @Test
    void badRequestException() {
        BadRequestException ex = new BadRequestException("bad");
        assertEquals("bad", ex.getMessage());
    }

    @Test
    void resourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("not found");
        assertEquals("not found", ex.getMessage());
    }

    @Test
    void policyOperationException() {
        PolicyOperationException ex = new PolicyOperationException("conflict");
        assertEquals("conflict", ex.getMessage());
    }
}

