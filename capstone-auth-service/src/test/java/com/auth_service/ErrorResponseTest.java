package com.auth_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.auth_service.exception.ErrorResponse;

class ErrorResponseTest {

    @Test
    void errorResponse_fieldsSetCorrectly() {
        ErrorResponse response =
                new ErrorResponse(400, "BAD_REQUEST", "Invalid input");

        assertEquals(400, response.getStatus());
        assertEquals("BAD_REQUEST", response.getError());
        assertEquals("Invalid input", response.getMessage());
        assertNotNull(response.getTimestamp());
    }
}

