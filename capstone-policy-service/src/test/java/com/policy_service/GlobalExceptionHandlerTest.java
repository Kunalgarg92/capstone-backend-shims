package com.policy_service;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import com.policy_service.exception.BadRequestException;
import com.policy_service.exception.ErrorResponse;
import com.policy_service.exception.GlobalExceptionHandler;
import com.policy_service.exception.PolicyOperationException;
import com.policy_service.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    @Test
    void handleNotFound() {
        Mockito.when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response =
                handler.handleNotFound(
                        new ResourceNotFoundException("not found"),
                        request
                );

        assertEquals(404, response.getStatusCode().value());
        assertEquals("RESOURCE_NOT_FOUND", response.getBody().getError());
    }

    @Test
    void handleBadRequest() {
        Mockito.when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response =
                handler.handleBadRequest(
                        new BadRequestException("bad"),
                        request
                );

        assertEquals(400, response.getStatusCode().value());
        assertEquals("BAD_REQUEST", response.getBody().getError());
    }

    @Test
    void handleConflict() {
        Mockito.when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response =
                handler.handlePolicyOperation(
                        new PolicyOperationException("conflict"),
                        request
                );

        assertEquals(409, response.getStatusCode().value());
        assertEquals("POLICY_OPERATION_ERROR", response.getBody().getError());
    }

    @Test
    void handleConstraintViolation() {
        Mockito.when(request.getRequestURI()).thenReturn("/test");

        ResponseEntity<ErrorResponse> response =
                handler.handleConstraintViolation(
                        new jakarta.validation.ConstraintViolationException("violation", null),
                        request
                );

        assertEquals(400, response.getStatusCode().value());
        assertEquals("CONSTRAINT_VIOLATION", response.getBody().getError());
    }
}
