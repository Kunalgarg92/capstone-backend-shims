package com.auth_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.auth_service.exception.ErrorResponse;
import com.auth_service.exception.GlobalExceptionHandler;

class GlobalExceptionHandlerValidationTest {

    @Test
    void handleMethodArgumentNotValidException() {
        // Mock BindingResult
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError =
                new FieldError("obj", "email", "Email is invalid");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        MethodArgumentNotValidException ex =
                mock(MethodArgumentNotValidException.class);

        when(ex.getBindingResult()).thenReturn(bindingResult);

        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<ErrorResponse> response =
                handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("VALIDATION_ERROR", response.getBody().getError());
        assertEquals("Email is invalid", response.getBody().getMessage());
    }
}
