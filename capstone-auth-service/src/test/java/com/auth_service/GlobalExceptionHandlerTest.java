package com.auth_service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.auth_service.exception.AuthenticationException;
import com.auth_service.exception.AuthorizationException;
import com.auth_service.exception.BadRequestException;
import com.auth_service.exception.ErrorResponse;
import com.auth_service.exception.GlobalExceptionHandler;
import com.auth_service.exception.ResourceNotFoundException;

import io.jsonwebtoken.JwtException;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleAuthenticationException() {
        ResponseEntity<ErrorResponse> res =
                handler.handleAuth(new AuthenticationException("invalid login"));

        assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
        assertEquals("UNAUTHORIZED", res.getBody().getError());
    }

    @Test
    void handleAuthorizationException() {
        ResponseEntity<ErrorResponse> res =
                handler.handleAuthorization(new AuthorizationException("forbidden"));

        assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
        assertEquals("FORBIDDEN", res.getBody().getError());
    }

    @Test
    void handleBadRequestException() {
        ResponseEntity<ErrorResponse> res =
                handler.handleBadRequest(new BadRequestException("bad data"));

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertEquals("BAD_REQUEST", res.getBody().getError());
    }

    @Test
    void handleNotFoundException() {
        ResponseEntity<ErrorResponse> res =
                handler.handleNotFound(new ResourceNotFoundException("user not found"));

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertEquals("NOT_FOUND", res.getBody().getError());
    }

    @Test
    void handleJwtException() {
        ResponseEntity<ErrorResponse> res =
                handler.handleJwt(new JwtException("invalid token"));

        assertEquals(HttpStatus.UNAUTHORIZED, res.getStatusCode());
        assertEquals("INVALID_TOKEN", res.getBody().getError());
    }

    @Test
    void handleGeneralException() {
        ResponseEntity<ErrorResponse> res =
                handler.handleGeneral(new RuntimeException("boom"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertEquals("INTERNAL_SERVER_ERROR", res.getBody().getError());
    }
}

