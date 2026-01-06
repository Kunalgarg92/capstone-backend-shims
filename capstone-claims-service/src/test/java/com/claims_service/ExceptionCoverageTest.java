package com.claims_service;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.claims_service.exception.BusinessException;
import com.claims_service.exception.GlobalExceptionHandler;
import com.claims_service.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionCoverageTest {

    @Test
    void businessException() {
        BusinessException ex = new BusinessException("err");
        assertEquals("err", ex.getMessage());
    }

    @Test
    void resourceNotFound() {
        ResourceNotFoundException ex = new ResourceNotFoundException("404");
        assertEquals("404", ex.getMessage());
    }
    
    @Test
    void globalExceptionHandler_notFound() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResourceNotFoundException ex =
                new ResourceNotFoundException("missing");

        ResponseEntity<?> response = handler.notFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
