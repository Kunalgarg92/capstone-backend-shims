package com.policy_service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.policy_service.exception.ErrorResponse;

class ErrorResponseTest {

    @Test
    void errorResponse_getters() {
        LocalDateTime now = LocalDateTime.now();

        ErrorResponse response =
                new ErrorResponse(now, 400, "BAD", "message", "/path");

        assertEquals(400, response.getStatus());
        assertEquals("BAD", response.getError());
        assertEquals("message", response.getMessage());
        assertEquals("/path", response.getPath());
        assertEquals(now, response.getTimestamp());
    }
}

