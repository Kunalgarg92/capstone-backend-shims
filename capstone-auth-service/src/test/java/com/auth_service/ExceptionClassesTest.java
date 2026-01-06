package com.auth_service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.auth_service.exception.AuthenticationException;
import com.auth_service.exception.AuthorizationException;
import com.auth_service.exception.BadRequestException;
import com.auth_service.exception.ResourceNotFoundException;

class ExceptionClassesTest {

    @Test
    void authenticationException_message() {
        AuthenticationException ex = new AuthenticationException("auth failed");
        assertEquals("auth failed", ex.getMessage());
    }

    @Test
    void authorizationException_message() {
        AuthorizationException ex = new AuthorizationException("no access");
        assertEquals("no access", ex.getMessage());
    }

    @Test
    void badRequestException_message() {
        BadRequestException ex = new BadRequestException("bad request");
        assertEquals("bad request", ex.getMessage());
    }

    @Test
    void resourceNotFoundException_message() {
        ResourceNotFoundException ex = new ResourceNotFoundException("not found");
        assertEquals("not found", ex.getMessage());
    }
}
