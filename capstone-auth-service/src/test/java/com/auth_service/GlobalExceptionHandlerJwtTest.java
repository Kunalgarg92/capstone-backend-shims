package com.auth_service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.auth_service.exception.ErrorResponse;
import com.auth_service.exception.GlobalExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Claims;

class GlobalExceptionHandlerJwtTest {

    @Test
    void handleExpiredJwtException() {
        ExpiredJwtException ex =
                new ExpiredJwtException(
                        (Header<?>) null,
                        (Claims) null,
                        "JWT expired");

        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<ErrorResponse> response =
                handler.handleExpiredJwt(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("TOKEN_EXPIRED", response.getBody().getError());
        assertEquals("JWT token has expired", response.getBody().getMessage());
    }
}
